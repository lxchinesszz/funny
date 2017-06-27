## 目录

- 每日一图
- 每日多图
- 上传图片
- 发说说
- 用户注册
- 用户登录

### 快速入门



*所有请求参数请求头均为application/json*

*响应信息:  `code` 为 0 为处理成功，其他为失败*



### 每日一图

#### 请求方法

`GET`

#### 请求地址

`funyanimal/v1`

#### 响应

`data`

```json
{
  "code":0,
  "message":"操作成功",
  "data":
    {
      "imgUrl":"",
      "author":"",
      "date":"",
      "text":""
    }
}
```







### 每日多图

#### 请求方法

`GET`

#### 请求地址

`funyanimal/v1/list`

#### 响应

`data`

```
{
  "code":0,
  "message":"操作成功",
  "data":[
    {
      "imgUrl":"",
      "author":"",
      "date":"",
      "text":""
    },
    {
      "imgUrl":"",
      "author":"",
      "date":"",
      "text":""
    }
  ]  
}
```





### 上传图片



#### 请求方法

`POST`

#### 请求地址

`TODO`

#### 请求参数

| 参数     | 类型     | 解释     |
| ------ | ------ | ------ |
| text   | String | 一句话    |
| author | String | 作者     |
| imgUrl | String | 图片七牛地址 |



#### 相应参数

```Json
{
  "code":0,
  "message":"操作成功"
}
```

