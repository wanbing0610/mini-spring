# mini-spring

## BeanFactory 
    1.注册BeanDefinition
    2.获取Bean

## Resource
    抽象成资源

## BeanDefinitionReader 负责充当BeanFactory和Resource的中间角色,用于加载资源 


## SingletonBeanRegistry 管理单例Bean的接口




## 疑问
    ArgumentValue类的构造器,可以只有ArgumentValue(Object value, String type)两个参数吗，形参name字段可以缺少？
    BeanDefinition的hasBeanClass的方法是用来干嘛的



## 问题
    A依赖与B，当创建A的时候，Autowired B，此时B的BeanDefinition没有加载进来，则此时会报 BeanDefinition找不到
    
