package ch.study.复习.java基础;

/**
 * 文 件 名: singleton
 * 创 建 人: 原成昊
 * 创建日期: 2021/6/24 15:47
 * 邮   箱: 188897876@qq.com
 * 修改备注：
 */

/**
 * 饿汉式 类加载的时候就实例化 不管有没有使用的 会造成不必要的内存浪费
 */
class Singleton {
    private static Singleton mSingleton = new Singleton();

    private Singleton() {
    }

    public static Singleton getInstance() {
        return mSingleton;
    }
}

/**
 * 懒汉式 为了处理并发 会有不必要的线程同步开销
 */
class Singleton2 {
    private static Singleton2 mSingleton2;

    private Singleton2() {
    }

    public static synchronized Singleton2 getInstance() {
        if (mSingleton2 == null) {
            mSingleton2 = new Singleton2();
        }
        return mSingleton2;
    }
}

/**
 * 双重检查模式（dcl）
 */

class Singleton3 {
    private static volatile Singleton3 mSingleton3;

    private Singleton3() {
    }

    public static Singleton3 getInstance() {
        if (mSingleton3 == null) {
            synchronized (Singleton3.class) {
                if (mSingleton3 == null) {
                    mSingleton3 = new Singleton3();
                }
            }
        }
        return mSingleton3;
    }
}
/**
 * - 第一次判空，省去了不必要的同步。第二次是在Singleton等于空时才创建实例。
 * - 使用volatile保证了实例的可见性。
 * - DCL在一定程度上解决了资源的消耗和多余的同步、线程安全等问题，但是在某些情况下会失效。
 * <p>
 * 假设线程A执行到instance = new Singleton()语句，看起来只有一行代码，但实际上它并不是原子操作，这句代码最终会被编译成多条汇编指令，它大致做了3件事：
 * <p>
 * 1）给instance的实例分配内存。
 * <p>
 * 2）调用Singleton()构造函数，初始化成员字段。
 * <p>
 * 3）将instance对象指向分配的内存空间（此时instance就不是null了）。
 * <p>
 * 但是，由于Java编译器允许处理器乱序执行，以及JDK1.5之前JMM中的Cache、寄存器到主内存回写顺序的规定，上面的2和3的顺序是无法保证的，也就是说，执行顺序可能是1-2-3也可能是1-3-2。如果是后者，并且在3执行完毕、2未执行之前，被切换到线程B上，这时候instance因为已经在线程A内执行过了3，instance已经是非空了，所以，线程B直接取走instance，再使用时就会出错，这就是DCL失效问题，而且这种难以跟踪难以重现的错误可能会隐藏很久。
 * <p>
 * 在JDK1.5之后，SUN官方已经注意到这种问题，调整了JVM，具体化了volatile关键字，因此，如果JDK1.5或之后的版本，只需要将instance的定义改成private volatile static Singleton instance = null就可以保证instance对象每次都是从主内存中读取，就可以使用DCL的写法来完成单例模式。当然，volatile或多或少也会影响到性能，但考虑到程序的正确性，这点牺牲也是值得的。
 * <p>
 * DCL优点：资源利用率高，第一次执行getInstance时单例对象才会被实例化，效率高。
 * <p>
 * 缺点：第一次加载稍慢，也由于JMM的原因导致偶尔会失败。在高并发环境下也有一定的缺陷，虽然发生概率很小。DCL模式是使用最多的单例实现方式，它能够在需要时才实例化对象，并且能在绝大多数场景下保证对象的唯一性，除非你的代码在并发场景比较复杂或低于JDK1.6版本下使用，否则，这种方式一般能够满足要求。
 */


/**
 * 静态内部类单例模式
 * 第一次调用getInstance方法时虚拟机才加载SingletonHolder并初始化sInstance，这样保证了线程安全和实例的唯一性
 */
class Singleton4 {
    private Singleton4() {
    }

    public static Singleton4 getInstance() {
        return SingletonHolder.singleton4;
    }

    private static class SingletonHolder {
        private static Singleton4 singleton4 = new Singleton4();
    }
}

/**
 * 枚举单例
 * - 默认枚举实例的创建是线程安全的，并且在任何情况下都是单例。
 * - 简单、可读性不高。
 */
enum Singleton5 {
    INSTANCE;

    public void doSomeThing() {

    }
}
