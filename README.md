### Boulder Dash Rocks! (DS) Map Generator
* This Java command line tool extracts PNG maps from the original game ROM.
* Get [NDS-Header-Tool](https://digiex.net/threads/nds-header-tool-2-view-info-on-nds-roms.14990/)
  and use it to extract all files from your ROM.
* You need the `Levels/*.bdl` files and optionally (but highly **recommended**) `IniFiles/Levels.ini`
* `Levels.ini` contains the correct order of the levels.

### HOWTO
* Install [Java 17](https://adoptium.net/) or higher
* Unpack `bd-map.zip`
```
    Usage: MakeMap [level.bdl] ...
    
    Convert Boulder Dash Rocks level into a PNG map.
    
    Options:
     -levelsIniFile <filename>  Levels.ini filename
     -toc <filename>            Create HTML file which links all PNG maps
```
* Example usage:<br>
```
    $ MakeMap -levelsIniFile Levels.ini PlanetTour_JungleWorld_01.bdl
``` 
  will create `world1-01-PlanetTour_JungleWorld_01.png`
  ![](world1-01-PlanetTour_JungleWorld_01.png)

### Cheat Codes ###
from [jeuxvideo.com](https://www.jeuxvideo.com/wikis-soluce-astuces/cheat-codes/225774)

* **Activate cheat mode**<br>
  During a game: Hold L and press R, Up, X, X, Down, R, R.<br>
  "Cheatmode activated" appears on the upper screen.

* **Unlock all levels, characters, bonuses and game modes**<br>
  Hold L and press Down.<br>
  
* **Win the current level**<br>
  Hold L and press X.

* **Obtain 5 Super Diamonds**<br>
  Hold L and press Left.

* **Unlock "Slow Motion" mode (has no effect though, just a relict)**<br>
  Hold L and press Right.

* **Disable collisions**<br>
  Hold L and press Up.

* **Remove cheat messages from the screen**<br>
  Hold L, R and A.
