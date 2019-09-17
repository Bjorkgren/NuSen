# NuSen 

![GitHub top language](https://img.shields.io/github/languages/top/Bjorkgren/NuSen)
![GitHub last commit](https://img.shields.io/github/last-commit/Bjorkgren/NuSen) 

Android app that displays local weather both now ("Nu") and later ("Sen").

## Has

* Fast boot (finding location and querying weather-providers)
* Fresh data.

### Providers
(not affiliated)

```java
//TODO: Test if fmi and weather.gov return actual data, not just test data.
```
* SMHI.se
* YR.no
* FMI.fi
* WEATHER.gov

### General TODOS:
* Move find-location-start-func to OnCreate, instead of OnResume
* move row 115 __searching = false;__  to row 207, above -> __skylt.setText("\u00A0" + p.toUpperCase() + "\u00A0");__
* Save location name and location lat/lon to global variable, to keep from seraching on every onResume...
* Remove settings, set active hours to 7-22.
* Set interesting hours to 7, 12, 17, 22. 
 => Set the current hour to the value closest following.
 => Set the "sen" hour to the our after the chosen one above.
* Ex: Time is 10, show 12 and 17. Time is 8, show 12 and 17. Time is 20, show 22 and 07 (next morn)
