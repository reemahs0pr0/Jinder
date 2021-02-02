import pandas as pd
from sqlalchemy import create_engine

jobsData = pd.read_csv('fullmonsters.csv')
engine = create_engine('mysql+pymysql://root:@U1421137C6490@localhost:3306/jinder')
jobsData.to_sql('jobs', con=engine, if_exists='append', index=True, index_label='id')