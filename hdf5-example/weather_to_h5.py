import pandas as pd

daily_weather = pd.read_csv("/home/james/git/jhdf-webinar/hdf5-example/weather.csv")

print(daily_weather.describe())

daily_weather.to_hdf("weather.hdf5", key="weather", mode="w", complevel=8, format="table", data_columns=True)
