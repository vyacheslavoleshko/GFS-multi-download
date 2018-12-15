# GFS downloader
Small Java-library to automate downloads of Global Forecast System data from [NOMADS NOAA web-site](http://nomads.ncep.noaa.gov/)

Official web-site provides an ability to manually download one grib2 file per request, but we want more! ;)
## How it works
After downloading *.grb2 files will be stored in specified directory along with metadata .txt file describing parameters 
of downloaded files

`GfsDownloader` class provides a client-api for downloads.

 It supports 3 methods:
 
1. 
```java
public void download(ProductName productName, 
                     LocalDate dateOfForecast,
                     ModelRunCycle modelRunCycle,
                     List<ForecastHour> forecastHours,
                     List<Level> levels,
                     List<Variable> variables,
                     SpatialExtent spatialExtent,
                     Path rootDirectory)
```                    
It is the most basic and the most configurable method. You need to provide arguments: 

`productName:` GFS data with `0.25`, `0.50` and `1.00` degree resolution is supported (choose necessary from enum).

`dateOfForecast:` specify date when forecast was made.

`modelRunCycle:` specify model run cycle(forecasts are made 4 times each day - choose appropriate from enum).

`forecastHours:` specify list of forecast hours for which forecasts will be downloaded. If forecast for specified hour is 
not available on server, it will be skipped. Forecasts are available on 16 days ahead (384 hours) so maximum requested 
range could be retrieved using `ForecastHour.getRange(ForecastHour.HOUR_0, ForecastHour.HOUR_384);`
 
`levels:` specify height levels for forecasted variables (choose from enum).

`variables:` specify forecasted variables (choose from enum). 

See all available variables and levels [here](http://www.nco.ncep.noaa.gov/pmb/products/gfs/) in "Iventory" section

`spatialExtent:` you can download grib2 files for specific spatial extent. 
Create new instance of SpatialExtent object providing `leftlon, rightlon, toplat, bottomlat` in degrees

`rootDirectory:` specify directory on your file system to store forecasts. Downloaded files as well as metadata file will be put under this 
directory 

2. 
```java
public void downloadLatest(ProductName productName,
                           List<ForecastHour> forecastHours,
                           List<Level> levels,
                           List<Variable> variables,
                           SpatialExtent spatialExtent,
                           Path rootDirectory)
```
This method downloads latest forecasts available on server

3.
```java
public void downloadLatest(ProductName productName, 
                           Path rootDirectory)
```
This method downloads latest forecasts available on server (all forecast hours, variables for all levels for entire 
globe will be downloaded)

## Code examples
### Example 1
Downloads GFS data with 0.25 degree resolution. Forecast is made yesterday at 18 a.m. Forecasts on 2 days
ahead are needed for temperature and pressure on the surface and on 80 m above the ground for a region 
of Saint-Petersburg, Russia. Downloaded *.grib2 files and metadata should be stored under C:\\ directory
```java
import download.GfsDownloader;
import dto.SpatialExtent;
import enumeration.*;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class SimpleDemo {
    public static void exampleDownload() throws IOException, InterruptedException {
               
        ProductName productName          = ProductName.GFS_025_DEGREE;
        LocalDate forecastDate           = LocalDate.now().minusDays(1);
        ModelRunCycle modelRunCycle      = ModelRunCycle.SIX_AM;
        List<ForecastHour> forecastHours = ForecastHour.getRange(ForecastHour.HOUR_0, ForecastHour.HOUR_48);
        List<Level> levels               = Arrays.asList(Level.LEV_SURFACE, Level.LEV_80_M_ABOVE_GROUND);
        List<Variable> variables         = Arrays.asList(Variable.TMP, Variable.PRES);
        SpatialExtent spatialExtent      = new SpatialExtent(30, 31, 60, 59);
        Path rootDir                     = Paths.get("C:\\");

        new GfsDownloader().download(productName, forecastDate, modelRunCycle, forecastHours, levels, variables, spatialExtent, rootDir);
    }
}
```
### Example 2
Downloads latest available on server GFS data with 0.50 degree resolution. Forecast for next day
is needed for all available levels and variables for a region of Saint-Petersburg, Russia.
Downloaded *.grib2 files and metadata should be stored under C:\\ directory
```java
import download.GfsDownloader;
import dto.SpatialExtent;
import enumeration.*;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

public class SimpleDemo {
    public static void exampleDownloadLatest() throws IOException, InterruptedException {
        
        ProductName productName             = ProductName.GFS_050_DEGREE;
        List<ForecastHour> forecastHours    = ForecastHour.getRange(ForecastHour.HOUR_24, ForecastHour.HOUR_48);
        List<Level> levels                  = Collections.singletonList(Level.ALL);
        List<Variable> variables            = Collections.singletonList(Variable.ALL);
        SpatialExtent spatialExtent         = new SpatialExtent(30, 31, 60, 59);
        Path rootDir                        = Paths.get("C:\\");

        new GfsDownloader().downloadLatest(productName, forecastHours, levels, variables, spatialExtent, rootDir);
    }
}
```
### Example 3
Downloads latest available on server GFS data with 1.00 degree resolution. Downloads all available forecasts
which were made on this date, for all variables and all height levels. Downloaded *.grib2 files and metadata
should be stored under C:\\
```java
import download.GfsDownloader;
import enumeration.*;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SimpleDemo {
    public static void exampleDownloadLatest_2() throws IOException, InterruptedException {
        
        ProductName productName = ProductName.GFS_1_DEGREE;
        Path rootDir            = Paths.get("C:\\");

        new GfsDownloader().downloadLatest(productName, rootDir);
    }
}
```