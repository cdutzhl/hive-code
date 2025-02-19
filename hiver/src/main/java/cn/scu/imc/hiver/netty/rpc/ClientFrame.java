package cn.scu.imc.hiver.netty.rpc;

import cn.scu.imc.hiver.netty.rpc.client.ClientInit;
import cn.scu.imc.hiver.utils.PropertiesUtils;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 *类说明：rpc框架的客户端代理部分,交给Spring 托管
 * 1、动态代理的实现中，不再连接服务器，而是直接发送请求
 * 2、客户端网络部分的主体，包括Netty组件的初始化，连接服务器等
 */
@Service
public class ClientFrame implements Runnable{

    private static final Log LOG = LogFactory.getLog(ClientFrame.class);

    private ScheduledExecutorService executor = Executors
            .newScheduledThreadPool(1);
    private Channel channel;
    private EventLoopGroup group = new NioEventLoopGroup();

    /*是否用户主动关闭连接的标志值*/
    private volatile boolean userClose = false;
    /*连接是否成功关闭的标志值*/
    private volatile boolean connected = false;

    @Autowired
    private ClientInit clientInit;


    public boolean isConnected() {
        return connected;
    }

    /*连接服务器*/
    public void connect(int port, String host) throws Exception {

        try {
            Bootstrap b = new Bootstrap();
            b.group(group).channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(clientInit);
            // 发起异步连接操作
            ChannelFuture future = b.connect(
                    new InetSocketAddress(host, port)).sync();
            channel = future.sync().channel();
            /*连接成功后通知等待线程，连接已经建立*/
            synchronized (this){
                this.connected = true;
                this.notifyAll();
            }
            future.channel().closeFuture().sync();
        } finally {
            if(!userClose){/*非用户主动关闭，说明发生了网络问题，需要进行重连操作*/
                System.out.println("发现异常，可能发生了服务器异常或网络问题，" +
                        "准备进行重连.....");
                //再次发起重连操作
                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            TimeUnit.SECONDS.sleep(1);
                            try {
                                // 发起重连操作
                                connect(Integer.valueOf(PropertiesUtils.getValueByKey("hive.netty.port")+""),
                                        PropertiesUtils.getValueByKey("hive.netty.host")+ "");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }else{/*用户主动关闭，释放资源*/
                channel = null;
                group.shutdownGracefully().sync();
                connected = false;
            }
        }
    }

    @Override
    public void run() {
        if ("OPEN".equals(PropertiesUtils.getValueByKey("hive.netty.start"))) {
            try {
                connect(Integer.valueOf(PropertiesUtils.getValueByKey("hive.netty.port") + ""), PropertiesUtils.getValueByKey("hive.netty.host") + "");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void close() {
        userClose = true;
        channel.close();
    }

    @PostConstruct
    public void startNet() throws InterruptedException {
        new Thread(this).start();
        while(!this.isConnected()){
            synchronized (this){
                this.wait();
            }
        }
        LOG.info("网络通信已准备好，可以进行业务操作了........");
    }

    @PreDestroy
    public void stopNet(){
        close();
    }

}
