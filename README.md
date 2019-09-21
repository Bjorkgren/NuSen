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
* Add temp and weather graphics
* Ask about weathers
* Save temps in an array, easier to calc median then.
* Do the same for weather-type-id, (generalize for providers first)
* When receiving weather, first loop until now-hour and then continue loop to later-hour is found, then exit the loop.
