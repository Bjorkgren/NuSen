# NuSen 

![GitHub top language](https://img.shields.io/github/languages/top/Bjorkgren/NuSen)
![GitHub last commit](https://img.shields.io/github/last-commit/Bjorkgren/NuSen) 

Android app that displays local weather both now ("Nu") and later ("Sen").

Gathers data from multiple providers to show less guessy data. 

If one provider says "rain" and the others say "no rain" then the app says "no rain". 

If 2 providers say "sun", and one say "rain" and one say "clouds", then the app says "sun".

## Has..

* ..fast boot (finding location and querying weather-providers).
* ..fresh data.
* ..a pretty street sign.

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
 => Set the current hour to the value closest following.
 => Set the "sen" hour to the our after the chosen one above.
* Ex: Time is 10, show 12 and 17. Time is 8, show 12 and 17. Time is 20, show 22 and 07 (next morn)
