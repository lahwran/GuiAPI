GuiAPI
======

GuiAPI uses the TWL library from Matthias Mann, see [[http://twl.l33tlabs.org/|twl.l33tlabs.org]]

Building
--------

* Currently, GuiAPI uses MCP for the most part, so you are going to need to decompile a jar with it. To start, we need to make sure we have all the needed references, so before you decompile install the following:
    - twl/bin/*
    - xpp3-1.1.4c/*
    - theme/*
* Add ModLoader or any other APIs you want to use at this point. ModLoader is not required to build this though.
* Decompile with MCP. It shouldn't decompile anything you added though, but that's OK.
* After that, drag in all the mcp source files from mcp/* EXCEPT for GuiOptions.java.
    - That's a base class, so we are going to edit the small changes we made in. I'm not going to go into much detail, since you can see the finished file, but I'll explain a bit. The code itself you can view in the patched GuiOptions.java.
* Now we need to patch GuiOptions.java to add the Global Mod Settings button.
    - In public void initGui(), you need to add the Global Mod Settings button. Add it in second last, before gui.done.
    - Then, move options.video and options.controls up slightly in the same method. I suggest getting rid of the + 12 parts for each, as I did.
    - Now we need to add the button handler. So in protected void actionPerformed(GuiButton var1) add a handler for the ID you used. I used 300.
* Now recompile and test! You are all done. Again, if you have any issues patching GuiOptions.java just look at the prepatched version.

Packaging
---------

To create a distributable archive, package twl/bin/*, xpp*/*, theme/*, and bin/*. The only base Minecraft class this edits is GuiOptions.

Credits
-------

- lahwran
- ShaRose
- _303
- Lots of people who I forget. Open an issue or something if you helped...

Documentation
-------------

Read all javadocs at https://dl.dropbox.com/u/3687570/GuiAPI%20Javadoc/index.html

They are updated as I commit. Note the documentation isn't that great (It's not my strong point), I just tried to tag everything and explain roughly what it does.
