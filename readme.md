GuiAPI
======

GuiAPI uses the TWL library from Matthias Mann, see http://twl.l33tlabs.org/

Building
--------

* Decompile Minecraft with MCP. If you are using forge, use that install script. Basically start as if you weren't going to use GuiAPI at all.
* Now, download the Dev package you need for your version of GuiAPI. For example, if you were developing for 0.15.1, since there's no 0.15.1 dev package, you would download the dev package for 0.15.0. Copy the contents of this into Minecraft.jar.
* After that, drag in all the mcp source files from mcp/* into the src\minecraft\net\minecraft\src directory in MCP.
* Now recompile and test! You are all done.

Packaging
---------

To create a distributable archive, package twl/bin/*, xpp*/*, theme/*, and bin/*.

Credits
-------

- lahwran
- ShaRose
- _303
- Lots of people who I forget. Open an issue or something if you helped...

Documentation
-------------

All releases should be tagged here on github, and you can view the docs there. I haven't bothered setting up proper javadocs hosting on github yet.