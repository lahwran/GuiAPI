/**
 * 303's reflection callback api. warning: dangerously awesome. modified to be
 * usable as a runnable. <br/>
 * <br/>
 * example: <br/>
 * <br/>
 * assuming x.addCallback takes a runnable: <br/>
 * public void F(T x) <br/>
 * { <br/>
 * x.addCallback(new ModAction(this, "callback")); <br/>
 * } <br/>
 * <br/>
 * public void callback() <br/>
 * { <br/>
 * //do stuff here <br/>
 * } <br/>
 * 
 * @author 303 (Patch by ShaRose)
 * @version 0.9.5
 */
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.security.InvalidParameterException;

public class ModAction implements Runnable {
    protected List<Object> handlerObjects = new ArrayList<Object>();
    protected List<String> handlerMethods = new ArrayList<String>();

    protected Object[] defaultArguments;

    @SuppressWarnings("unchecked")
    protected Class[] params;
    /**
     * coming soon, this will be useful enough to document
     */
    public String name;
    /**
     * coming soon, this will be useful enough to document
     */
    public Object data;

    /**
     * "normal" constructor.
     * @param name_ name used in error messages. set to something you'll
     *            recognize.
     * @param params_ paramaters types that you will pass through call to your
     *            handlers.
     */
    @SuppressWarnings("unchecked")
    public ModAction(String name_, Class... params_)
    {
        name = name_;
        params = params_;
    }

    /**
     * condensed constructor, for use when a runnable is needed. combines
     * constructor and call to addhandler.
     * @param o object on which to call handler
     * @param method handler method name
     * @param params_ you won't want to pass any into this for runnables, but
     *            same meaning as for the normal constructor.
     */
    @SuppressWarnings("unchecked")
    public ModAction(Object o, String method, Class... params_)
    {
        name = method; // cheating?
        params = params_; // will usually be empty in this constructor
        addHandler(o, method);
    }

    /**
     * coming soon, this will be useful enough to document
     * @param o -
     * @param method -
     * @param _name -
     * @param params_ -
     */
    @SuppressWarnings("unchecked")
    public ModAction(Object o, String method, String _name, Class... params_)
    {
        name = _name; // cheating?
        params = params_; // will usually be empty in this constructor
        addHandler(o, method);
    }

    /**
     * coming soon, this will be useful enough to document
     * @param o -
     * @param method -
     * @param _data -
     * @param params_ -
     */
    @SuppressWarnings("unchecked")
    public ModAction(Object o, String method, Object _data, Class... params_)
    {
        name = method; // cheating?
        data = _data;
        params = params_; // will usually be empty in this constructor
        addHandler(o, method);
    }

    /**
     * adds a handler.
     * @param o object on which to call the handler
     * @param method method on object to call for handler
     * @return this (for chaining calls)
     */
    @SuppressWarnings("unchecked")
    public ModAction addHandler(Object o, String method)
    {
        try
        {
            @SuppressWarnings("unused")
            Method meth = GetMethodRecursively(o, method);// (o instanceof Class
                                                          // ? (Class<?>)o :
                                                          // o.getClass()).getDeclaredMethod(method,
                                                          // params);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new RuntimeException("invalid method parameters");
        }

        for (int i = 0; i < handlerObjects.size(); ++i)
        {
            Object oo = handlerObjects.get(i);
            String omethod = handlerMethods.get(i);

            if (oo == o && omethod.equals(method))
            {
                System.err.println(String.format("WARNING: event handler is already registered: %s: %s.%s", name, o.getClass().getName(), method));
                return this;
            }
        }

        handlerObjects.add(o);
        handlerMethods.add(method);

        return this;
    }

    /**
     * call all handlers.
     * @param args arguments list to pass to handlers - make sure it matches
     *            what you passed in in the constructor.
     * @return list of what each handler returns.
     */
    @SuppressWarnings("unchecked")
    public Object[] call(Object... args)
    {
        Object[] returns = new Object[handlerObjects.size()];
        for (int i = 0; i < handlerObjects.size(); ++i)
        {
            Object o = handlerObjects.get(i);
            String method = handlerMethods.get(i);

            try
            {
                Method meth = GetMethodRecursively(o, method);// (o instanceof
                                                              // Class ?
                                                              // (Class<?>)o :
                                                              // o.getClass()).getDeclaredMethod(method,
                                                              // params);
                returns[i] = meth.invoke(o instanceof Class ? null : o, args);
            }
            catch (Exception e)
            {
                e.printStackTrace();
                throw new RuntimeException("error calling callback");
            }
        }
        return returns;
    }

    public boolean hasHandler(Object o, String method)
    {
        for (int i = 0; i < handlerObjects.size(); ++i)
        {
            Object oo = handlerObjects.get(i);
            String omethod = handlerMethods.get(i);

            if (oo == o && omethod.equals(method)) { return true; }
        }

        return false;
    }

    public boolean removeHandler(Object o, String method)
    {
        for (int i = 0; i < handlerObjects.size(); ++i)
        {
            Object oo = handlerObjects.get(i);
            String omethod = handlerMethods.get(i);

            if (oo == o && omethod.equals(method))
            {
                handlerObjects.remove(i);
                handlerMethods.remove(i);
                return true;
            }
        }

        return false;
    }

    Method GetMethodRecursively(Object o, String method) // Why do this? Because
                                                         // Class.getMethod
                                                         // doesn't include
                                                         // private methods, and
                                                         // Class.getDeclaredMethod
                                                         // doesn't return
                                                         // superclass methods.
    {
        Class<?> currentclass = (o instanceof Class ? (Class<?>)o : o.getClass());
        while (true)
        {
            try
            {
                if (currentclass == null) // End of the line, return null.
                    return null;

                Method returnval = currentclass.getDeclaredMethod(method, params);
                if (returnval != null)
                {
                    returnval.setAccessible(true);
                    return returnval; // hey look at that, a method!
                }
            }
            catch (Throwable x)
            {} // Exception? Oh well!
            currentclass = currentclass.getSuperclass(); // We don't need to
                                                         // worry about
                                                         // exceptions here, if
                                                         // it's a primitive
                                                         // type or something it
                                                         // will return null on
                                                         // the next loop.
        }
    }

    /**
     * This sets the arguments to use when using run().
     * If this is not used, run calls the requested method with no arguments.
     * Throws InvalidParameterException if the arguments provided do not match the method parameters, or are unassignable to those types.
     */
    @SuppressWarnings("rawtypes")
	public ModAction setDefaultArguments(Object... Arguments)
    {
    	if(Arguments.length != params.length)
    		throw new InvalidParameterException("Arguments do not match the parameters.");
    	for (int i = 0; i < params.length; i++) {
    		Class value = params[i];
			if(!value.isInstance(Arguments[i]))
				throw new InvalidParameterException("Arguments do not match the parameters.");
		}
    	defaultArguments = Arguments;
    	return this;
    }
    
    /**
* Runnable call. If no default arguments are present it will call the method with no arguments. If the method has parameters, it will throw an exception.
* If default arguments are provided using setDefaultArguments, it will use those arguments.
*/
    @Override
    public void run()
    {
    	if(defaultArguments == null)
    		call();
    	else
    		call(defaultArguments);
    }
}