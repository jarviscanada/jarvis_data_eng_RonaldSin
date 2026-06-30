# Introduction
A UK based online store selling giftware, London Gift Store (LGS), has been running for 10 years but revenue has been stagnent. LGS has hired Jarvis consulting to deliver a proof of concept project that deliveries analytics the LGS marketing team can use to attract new and existing customers. A presentation was created based on the findings detailing the insights uncovered and reccomendations for next steps. LGS has provided an anonymized SQL file. This file was loaded into dockerized postgresql. The database was loaded into a jupyter notebook and using numpy and pandas data cleaning and manipulation was preformed to generate insights. The project is focused on looking at potential growth outside of the home region of the UK. 


# Implementaion
## Project Architecture
![image](diagram.png)
LGS Servers - Data is collected by the LGS team and stored in a azure database
SQL File - LGS team provides us with a anonymized SQL file of the data 
Postgres - The SQL file is loaded into a Dockerized Postgres file
Jupyter Notebook - The SQL data is loaded into a Pandas dataframe for manipulation
Pandas/Numpy - The data is cleaned and transformed in order to generate insights
Conclusions - Based on the insights meaningful business actionables and plans are created


## Data Analytics and Wrangling
[Notebook Link](./python_data_wrangling/retail_data_analytics_wrangling.ipynb)
- Create a link that points to your Jupyter notebook (use the relative path `./retail_data_analytics_wrangling.ipynb`)
How much of income comes from outside the UK - ~17.03% of income comes from outside the UK - There is a significant market outside of the UK and LGS should look into marketing and expanding the non UK customerbase
Which countries should we prioritize - EIRE, Netherlands, Germany, France make up ~64.97% of all non UK income. EIRE in paticular has a low number of customers with a very high spending amount per customers - LGS should heavily prioritize current and looking to expand EIRE customerbase
Do sales trends differ from the UK - Spending trends seem to mostly not differ from the UK outside of the UK - LGS should begine sending seasonal UK promotions to non UK countries as a low cost effective way to grow non UK market
What items sell the best outside of the UK - Red Toadstool led night light is overall the most popular item - LGS should use this info to effectively market and stock items for non UK countries

# Improvements
- easy to read and manipulate dashboadr would make it easy and quick for non technical users to discover new insights
- automate pipeline to take live user data and generate regular reports making it possible to quickly react and capitalize on new trends
- work with LGS team to clean and develop new data schema and database design, currently the dataset is very dirty with lots of unsuable data a clean datasset would ensure a solid base in which to get new insights from