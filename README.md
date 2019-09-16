# NuSen 

![GitHub top language](https://img.shields.io/github/languages/top/Bjorkgren/NuSen)
![GitHub last commit](https://img.shields.io/github/last-commit/Bjorkgren/NuSen) 

Android app that displays local weather both now ("nu") and later ("sen").

## Has

* Fast boot, remembers location.
* Updates weather every 30 min.
* Can set later ("sen") to be between 1-6hrs.
* Can set relevant times (ex. 7:00 - 22:00)

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
* Remove settings, set active hours to 7-22.
* Set interesting hours to 7, 12, 17, 22. 
 => Set the current hour to the value closest.
 => Set the "sen" hour to the our after the chosen one above.
* Ex: Time is 10, show 12 and 17. Time is 9, show 7 and 12. Time is 20, show 22 and 07 (next morn)
