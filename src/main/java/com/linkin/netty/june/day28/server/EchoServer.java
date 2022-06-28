package com.linkin.netty.june.day28.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

public class EchoServer {

    private final Integer port;

    public EchoServer(Integer port) {
        this.port = port;
    }

    public static void main(String[] args) throws InterruptedException {

        int port = 9999;
        EchoServer echoServer = new EchoServer(port);
        echoServer.start();
    }

    public void start() throws InterruptedException {
        final EchoServerHandler handler = new EchoServerHandler();

        // 1. 使用NIO接受和处理新的连接
        EventLoopGroup group = new NioEventLoopGroup();
        try {

            ServerBootstrap b = new ServerBootstrap();
            b.group(group)
                    .channel(NioServerSocketChannel.class)
                    .localAddress(new InetSocketAddress(port))
                    // ChannelInitializer关键， 当一个新连接被接受的时候，会创建一个新的子Channel
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) {
                            // 然后ChannelInitializer会将EchoServerHandler加入到该Channel的pipeline中
                            // ChannelHandler会受到请求消息的通知
                            socketChannel.pipeline().addLast(handler);
                        }
                    });
            // 同步方法, 将会阻塞线程, 一直到绑定操作完成为止,绑定啥? 端口?
            ChannelFuture f = b.bind().sync();
            f.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully().sync();
        }
    }
}
