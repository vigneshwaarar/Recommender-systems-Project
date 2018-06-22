
# coding: utf-8

# In[1]:


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
import matplotlib.pyplot as plt
from sklearn import linear_model, metrics
from sklearn.metrics import mean_absolute_error


# In[2]:


pd_data = pd.read_csv('data.csv',names= ["user", "item", "r1", "r2", "r3", "r4", "r5", "r6", "r7", "r8", "r9"])


# In[3]:


users  = len(pd_data.user.unique())
items = len(pd_data.item.unique())
print "total number of users {}".format(users)
print "total number of items {}".format(items)


# In[4]:


pdata = pivot_table(pd_data, values='r1', index=['user'], columns=['item'])
pdata.fillna(0,inplace=True)


# In[376]:


def similarity_user(data):
    user_similarity_cosine = np.zeros((users,users))
    #user_similarity_jaccard = np.zeros((users,users))
    for user1 in range(users):
        for user2 in range(users):
            if np.count_nonzero(data.iloc[user1,:].tolist()) and np.count_nonzero(data.iloc[user2,:].tolist()):
                user_similarity_cosine[user1][user2] = 1-scipy.spatial.distance.cosine(data.iloc[user1,:].tolist(),data.iloc[user2,:].tolist())
                #user_similarity_jaccard[user1][user2] = 1-scipy.spatial.distance.jaccard(data.iloc[user1,:].tolist(),data.iloc[user2,:].tolist())
    return user_similarity_cosine
    


# In[377]:


def cal_new_sim(pdata,top3_neigbours,cosine_sim):
    error=0
    count=0
    cal_data =pdata
    for u in range(users):
        top3_sim = top3_neigbours[u]
        print top3_sim ,"user",u+1
        denom = sum(cosine_sim[u][each_s]for each_s in top3_sim)
        for e_item in range(items): 
            temp =pdata.iloc[top3_sim[0],e_item]*cosine_sim[u][top3_sim[0]]+pdata.iloc[top3_sim[1],e_item]*cosine_sim[u][top3_sim[1]]+pdata.iloc[top3_sim[2],e_item]*cosine_sim[u][top3_sim[2]]
            cal_sim =  temp/denom
            o_sim= cal_data.iloc[u][e_item+1]
            if int(cal_data.iloc[u][e_item+1]) != 0:
                cal_data.iloc[u][e_item+1]=cal_sim
                error += abs(o_sim-cal_sim)
                count+=1
    return cal_data


# In[ ]:


final =pd.DataFrame()


# In[451]:


for total_items in range(3,9):
    curent_item= 'r'+str(total_items)
    current_data = pivot_table(pd_data, values=curent_item, index=['user'], columns=['item'])
    current_data.fillna(0,inplace=True)
    a = current_data
    cosine_sim =similarity_user(a)
    top3_neigbours=[]
    for ii in cosine_sim:
        top3_neigbours.append(sorted(range(len(ii)), key=lambda i: ii[i])[-4:-1])
    new_data =cal_new_sim(a,top3_neigbours,cosine_sim)
    d= new_data.reset_index()
    if final.empty:
        col=pd.melt(d, id_vars='user', var_name='item', value_name=curent_item)
        final =final.append(col)
    col=pd.melt(d, id_vars='user', var_name='item', value_name=curent_item)
    final[curent_item] = col[curent_item]


# In[454]:


final.to_csv('predicted_rating.csv')


# In[508]:


X_train = pd_data[[ "r1", "r2", "r3", "r4", "r5", "r6", "r7", "r8"]]
y_train =pd_data.r9
X_test = final[[ "r1", "r2", "r3", "r4", "r5", "r6", "r7", "r8"]]

X_train.fillna(0,inplace=True)
y_train.fillna(0,inplace=True)


# In[514]:


X_test = X_test[(X_test.T != 0).any()]
print X_test.shape

X_train = X_train[(X_train.T != 0).any()]
X_train.shape


# In[515]:


y_train = y_train[y_train>0]


# In[516]:


print y_train.shape
print 


# In[517]:


reg = linear_model.LinearRegression()
 
# train the model using the training sets
reg.fit(X_train, y_train)


# In[518]:


y_test=reg.predict(X_test)


# In[519]:


mean_absolute_error(y_test,y_train)


# In[520]:


# regression coefficients
print('Coefficients: \n', reg.coef_)


# In[521]:


# regression coefficients
print('Coefficients: \n', reg.coef_)
 
# variance score: 1 means perfect prediction
print('Variance score: {}'.format(reg.score(X_test, y_test)))
 
# plot for residual error
 
## setting plot style
plt.style.use('fivethirtyeight')
 
## plotting residual errors in training data
plt.scatter(reg.predict(X_train), reg.predict(X_train) - y_train,
            color = "green", s = 10, label = 'Train data')
 
## plotting residual errors in test data
plt.scatter(reg.predict(X_test), reg.predict(X_test) - y_test,
            color = "blue", s = 10, label = 'Test data')
 
## plotting line for zero residual error
plt.hlines(y = 0, xmin = 0, xmax = 50, linewidth = 2)
 
## plotting legend
plt.legend(loc = 'upper right')
 
## plot title
plt.title("Residual errors")
 
## function to show plot
plt.show()


# ## Average Case

# In[5]:


pd_data = pd.read_csv('data.csv',names= ["user", "item", "r1", "r2", "r3", "r4", "r5", "r6", "r7", "r8", "r9"])


# In[6]:


users  = len(pd_data.user.unique())
items = len(pd_data.item.unique())
print "total number of users {}".format(users)
print "total number of items {}".format(items)


# In[9]:


pd_data.head()


# In[10]:


pdata = pivot_table(pd_data, values='r1', index=['user'], columns=['item'])
pdata.fillna(0,inplace=True)


# In[11]:


pdata


# In[57]:


def similarity_user(data):
    user_similarity_cosine = np.zeros((users,users))
    user_similarity_jaccard = np.zeros((users,users))
    for user1 in range(users):
        for user2 in range(users):
            if np.count_nonzero(data.iloc[user1,:].tolist()) and np.count_nonzero(data.iloc[user2,:].tolist()):
                user_similarity_cosine[user1][user2] = 1-scipy.spatial.distance.cosine(data.iloc[user1,:].tolist(),data.iloc[user2,:].tolist())
                #user_similarity_jaccard[user1][user2] = 1-scipy.spatial.distance.jaccard(data.iloc[user1,:].tolist(),data.iloc[user2,:].tolist())
    return user_similarity_cosine
    


# In[65]:


def cal_new_sim(pdata,top3_neigbours,cosine_sim):
    error=0
    count=0
    cal_data =pdata
    for u in range(users):
        top3_sim = top3_neigbours[u]
        denom = sum(cosine_sim[u][each_s]for each_s in top3_sim)
        for e_item in range(items): 
            temp =pdata.iloc[top3_sim[0],e_item]*cosine_sim[u][top3_sim[0]]+pdata.iloc[top3_sim[1],e_item]*cosine_sim[u][top3_sim[1]]+pdata.iloc[top3_sim[2],e_item]*cosine_sim[u][top3_sim[2]]
            cal_sim =  temp/denom
            o_sim= cal_data.iloc[u][e_item+1]
            if int(cal_data.iloc[u][e_item+1]) != 0:
                cal_data.iloc[u][e_item+1]=cal_sim
                error += abs(o_sim-cal_sim)
                count+=1
    return cal_data,error/count


# In[66]:


mat_of_mat= np.zeros((users,users))


# In[67]:


mat_of_mat


# In[68]:


for total_items in range(1,10):
    curent_item= 'r'+str(total_items)
    current_data = pivot_table(pd_data, values=curent_item, index=['user'], columns=['item'])
    current_data.fillna(0,inplace=True)
    a = current_data
    cosine_sim =similarity_user(a)
    for i in range(cosine_sim.shape[0]):
        for j in range(cosine_sim.shape[1]):
            mat_of_mat[i][j]+= cosine_sim[i][j]
    


# In[69]:


mat_of_mat= mat_of_mat/10


# In[70]:


top3_neigbours=[]
for ii in mat_of_mat:
    top3_neigbours.append(sorted(range(len(ii)), key=lambda i: ii[i])[-4:-1])
r9 = pivot_table(pd_data, values='r9', index=['user'], columns=['item'])
r9.fillna(0,inplace=True)
new_data,e =cal_new_sim(r9,top3_neigbours,mat_of_mat)


# In[71]:


e


# In[72]:


mat_of_mat


# In[73]:


new_data

