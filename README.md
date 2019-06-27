## About
This uses my own custom game engine which uses AWT and the Graphics2D library. It's obviously not very good, but it was made in 2014 for my GCSE coursework and I at least had fun making it. Also the animations are hilarious. It's a simple 2D game (with scrolling) where the premise is that you are a 'spy' that has to infiltrate a volcanic lair that is erupting.  

I went overboard with the particle effects, pretty much everything has them. There's smoke blocking the hallways, lava particles as the base is consumed, particles when you lose/gain health. Even particles on the main menu and when you hold down the mouse button.
I added all sorts of small features to see how they could be implemented. Dynamic objects (eg. opening doors), laser sensors, buzzsaws, laptops that can be 'hacked' for bonus points, etc.

There's multiple levels, and you get a score on each level based on how quickly you complete it. If you beat the levels pre-existing score then you 'elite' it.

Example screenshots can be found [here](https://imgur.com/a/HwYFkqB)

## How to use
The project can be built from sources, or can be run by using the contents of `compiled`. The resources for the game (level data, audio, image resources, etc.) are stored outside of the game in the 'src' folder (because I hadn't figured out a better way to do that at the time). `data.properties` can be edited to tweak how the game is run. The main menu should give enough help and `F3` toggles debug information.
