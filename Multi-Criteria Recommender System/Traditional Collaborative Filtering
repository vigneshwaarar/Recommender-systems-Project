
# coding: utf-8

# In[135]:


import numpy as np
from pandas import pivot_table
import pandas as pd
import scipy.stats
import scipy.spatial
from sklearn.cross_validation import KFold
from sklearn.metrics import mean_squared_error
from math import sqrt
import math
import sys


# In[179]:



def readingFile(filename):
    f = open(filename,"r")
    data = []
    for row in f:
        r = row.split()
        e = [int(r[0]), int(r[1]), int(r[2])]
        data.append(e)
    return data


# In[180]:


data = readingFile("rating_overall.txt")


# In[183]:


data[1]


# In[181]:


np.count_nonzero(data[1])


# In[170]:


pddata = pd.DataFrame(all_data,columns = ["user", "item", "rating"])


# In[210]:


users  = len(pddata.user.unique())
items = len(pddata.item.unique())


# In[173]:


pdata = pivot_table(pddata, values='rating', index=['user'], columns=['item'])
pdata.fillna(0,inplace=True)
pdata


# In[187]:


def similarity_user(data):

    user_similarity_cosine = np.zeros((users,users))
    user_similarity_jaccard = np.zeros((users,users))

    for user1 in range(users):
        for user2 in range(users):
            
            if np.count_nonzero(data.iloc[user1,:].tolist()) and np.count_nonzero(data.iloc[user2,:].tolist()):
           
                user_similarity_cosine[user1][user2] = 1-scipy.spatial.distance.cosine(data.iloc[user1,:].tolist(),data.iloc[user2,:].tolist())
                
                #user_similarity_jaccard[user1][user2] = 1-scipy.spatial.distance.jaccard(data.iloc[user1,:].tolist(),data.iloc[user2,:].tolist())
    return user_similarity_cosine, user_similarity_jaccard
    


# In[188]:


s1,s2 =similarity_user(pdata)


# In[190]:


s1 [0]


# In[201]:


top3=[]
for ii in s1:
    top3.append(sorted(range(len(ii)), key=lambda i: ii[i])[-4:-1])


# In[253]:


error=0
count=0
cal_data =pdata
for u in range(users):
    
    top3_sim = top3[u]
    print top3_sim ,"user",u+1
    denom = sum(s1[u][each_s]for each_s in top3_sim)
    for e_item in range(items): 
        temp =pdata.iloc[top3_sim[0],e_item]*s1[u][top3_sim[0]]+pdata.iloc[top3_sim[1],e_item]*s1[u][top3_sim[1]]+pdata.iloc[top3_sim[2],e_item]*s1[u][top3_sim[2]]
        cal_sim =  temp/denom
        o_sim= cal_data.iloc[u][e_item+1]
        if int(cal_data.iloc[u][e_item+1]) != 0:
            cal_data.iloc[u][e_item+1]=cal_sim
            error += abs(o_sim-cal_sim)
            count+=1
    


# In[254]:


error/count

