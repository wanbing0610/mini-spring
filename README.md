# mini-spring

## BeanFactory 

## SingletonBeanRegistry
    DefaultSingletonBeanRegistry

## ConfigurableBeanFactory 
    方法侧重于Bean层面,例如添加bean的处理器
    实现类为AbstractBeanFactory

## AutowiredBeanFactory 
    实现类为AbstractAutowireCapableBeanFactory

## ListableBeanFactory
    
    

## ConfigurableListableBeanFactory 拥有上面所有接口的功能

    实现类为DefaultListableBeanFactory


## 疑问
    1.AutowiredAnnotationBeanPostProcessor#postProcessBeforeInitialization和postProcessAfterInitialization理解,
    对象不是已经初始化过了吗？
        第一步：经过new()实例化对象
        第二步：给实例化的对象赋值

    
