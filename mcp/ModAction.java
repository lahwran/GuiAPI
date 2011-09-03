package net.minecraft.src;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.reflect.Method;
import java.security.InvalidParameterException;
import java.util.ArrayList;


/**
 * This class is a helper designed to make it easier to use callbacks. It
 * implements Runnable and PropertyChangeListener, and you can use it in several
 * ways.
 * 
 * @author _303
 * @author ShaRose
 */
@SuppressWarnings("rawtypes")
public class ModAction implements Runnable, PropertyChangeListener
{
    /**
     * The 'dataRef' field is an extra, unused field you can use as a key, or
     * something specific to track.
     */
    public Object dataRef;
    protected Object[] defaultArguments;
    protected ArrayList<ModAction> mergedActions = new ArrayList<ModAction>();
    protected String methodName;
    protected Class[] methodParams = new Class[0];
    /**
     * The 'nameRef' field is also something you can use to track your
     * ModActions.
     */
    public String nameRef;
    protected Object objectRef;
    
    /**
     * This is the most common ModAction constructor. You simply specify what
     * has the method you want, and the method's name and (Optionally) the
     * parameters.
     * 
     * @param o
     *            The object reference or class that contains the method you
     *            wish to call.
     * @param method
     *            The name of the method you wish to call.
     * @param params
     *            The parameters of the method you wish to call.
     */
    public ModAction(Object o, String method, Class... params)
    {
        nameRef = method;
        methodParams = params;
        setupHandler(o, method);
    }
    
    /**
     * This is an overload to allow the dataRef object.
     * 
     * @param o
     *            The object reference or class that contains the method you
     *            wish to call.
     * @param method
     *            The name of the method you wish to call.
     * @param data
     *            A specific key for identifying this ModAction. Basically
     *            something you can use to track it.
     * @param params
     *            The parameters of the method you wish to call.
     */
    public ModAction(Object o, String method, Object data, Class... params)
    {
        this(o, method, params);
        dataRef = data;
    }
    
    /**
     * This is an overload to allow the nameRef string.
     * 
     * @param o
     *            The object reference or class that contains the method you
     *            wish to call.
     * @param method
     *            The name of the method you wish to call.
     * @param name
     *            The name of this ModAction. Something else you can use to keep
     *            track, and this is included within exceptions.
     * @param params
     *            The parameters of the method you wish to call.
     */
    public ModAction(Object o, String method, String name, Class... params)
    {
        this(o, method, params);
        nameRef = name;
    }
    
    /**
     * This is an overload to allow the nameRef string and dataRef object.
     * 
     * @param o
     *            The object reference or class that contains the method you
     *            wish to call.
     * @param method
     *            The name of the method you wish to call.
     * @param name
     *            The name of this ModAction. Something else you can use to keep
     *            track, and this is included within exceptions.
     * @param data
     *            A specific key for identifying this ModAction. Basically
     *            something you can use to track it.
     * @param params
     *            The parameters of the method you wish to call.
     */
    public ModAction(Object o, String method, String name, Object data,
            Class... params)
    {
        this(o, method, params);
        nameRef = name;
        dataRef = data;
    }
    
    /**
     * This is a constructor that is only supposed to be used internally. It
     * sets no actual handler, only a name.
     * 
     * @param name
     *            The name to use for this ModAction.
     */
    protected ModAction(String name)
    {
        nameRef = name;
    }
    
    @SuppressWarnings("unchecked")
    protected Boolean argsMatch(Class[] classTypes, Object[] arguments)
    {
        if (classTypes.length != arguments.length)
        {
            return false;
        }
        for (int i = 0; i < classTypes.length; i++)
        {
            if (!classTypes[i].isAssignableFrom(arguments[i].getClass()))
            {
                return false;
            }
        }
        return true;
    }
    
    /**
     * This calls this ModAction and any Merged actions with the provided
     * arguments. If the arguments do not match it will try using the default
     * arguments, if they exist. If not, it will throw an exception.
     * 
     * @param args
     *            The arguments to try and call for the ModAction (And any
     *            merged ModActions)
     * @return The return values for each ModAction.
     * @throws Exception
     *             Any exception thrown when the ModAction attempts to run.
     */
    public Object[] call(Object... args) throws Exception
    {
        try
        {
            if (mergedActions.isEmpty())
            {
                return new Object[]
                { callInt(args) };
            }
            else
            {
                Object[] returnvals = new Object[mergedActions.size()];
                for (int i = 0; i < returnvals.length; i++)
                {
                    returnvals[i] = mergedActions.get(i).callInt(args);
                }
                return returnvals;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new Exception("error calling callback '" + nameRef + "'.", e);
        }
    }
    
    protected Object callInt(Object... args) throws Exception
    {
        if (!argsMatch(methodParams, args))
        {
            if (defaultArguments != null)
            {
                args = defaultArguments;
            }
        }
        try
        {
            Method meth = GetMethodRecursively(objectRef, methodName);
            return meth.invoke(objectRef instanceof Class ? null : objectRef,
                    args);
        }
        catch (Exception e)
        {
            throw new Exception("error calling callback '" + nameRef + "'.", e);
        }
    }
    
    protected Method GetMethodRecursively(Object o, String method)
    {
        Class<?> currentclass = (o instanceof Class ? (Class<?>) o : o
                .getClass());
        while (true)
        {
            try
            {
                if (currentclass == null)
                {
                    return null;
                }
                Method returnval = currentclass.getDeclaredMethod(method,
                        methodParams);
                if (returnval != null)
                {
                    returnval.setAccessible(true);
                    return returnval;
                }
            }
            catch (Throwable x)
            {}
            currentclass = currentclass.getSuperclass();
        }
    }
    
    /**
     * This method merges ModActions together into one.
     * 
     * @param newAction
     *            The new Action to merge with.
     * @return The Merged ModAction.
     */
    public ModAction mergeAction(ModAction newAction)
    {
        if (mergedActions.isEmpty())
        {
            ModAction merged = new ModAction("Merged ModAction");
            merged.mergedActions.add(this);
            merged.mergedActions.add(newAction);
            return merged;
        }
        else
        {
            mergedActions.add(newAction);
            return this;
        }
    }
    
    @Override
    public void propertyChange(PropertyChangeEvent paramPropertyChangeEvent)
    {
        if ((methodParams.length != 1)
                || (methodParams[0] != PropertyChangeEvent.class))
        {
            throw new RuntimeException(
                    "invalid method parameters for a PropertyChangeListener callback. Modaction is '"
                            + nameRef + "'.");
        }
        try
        {
            call(paramPropertyChangeEvent);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new RuntimeException(
                    "Error when calling PropertyChangeListener callback. Modaction is '"
                            + nameRef + "'.", e);
        }
    }
    
    @Override
    public void run()
    {
        try
        {
            call();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new RuntimeException(
                    "Error when calling Runnable callback. Modaction is '"
                            + nameRef + "'.", e);
        }
    }
    
    /**
     * This sets the arguments to use if no or invalid arguments are provided.
     * Throws InvalidParameterException if the arguments provided do not match
     * the method parameters, or are not assignable to those types.
     * 
     * @param Arguments
     *            The arguments to try and call.
     * @return this
     */
    public ModAction setDefaultArguments(Object... Arguments)
    {
        if (!argsMatch(methodParams, Arguments))
        {
            throw new InvalidParameterException(
                    "Arguments do not match the parameters.");
        }
        defaultArguments = Arguments;
        return this;
    }
    
    protected void setupHandler(Object o, String method)
    {
        try
        {
            GetMethodRecursively(o, method);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new RuntimeException(
                    "Could not locate Method with included information.");
        }
        methodName = method;
        objectRef = o;
    }
}