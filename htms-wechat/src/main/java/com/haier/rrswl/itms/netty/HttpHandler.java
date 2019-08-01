package com.haier.rrswl.itms.netty;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;

/**
 * Handler需要声明泛型为<FullHttpRequest>，声明之后，只有msg为FullHttpRequest的消息才能进来。
 * 由于泛型的过滤比较简单，我们就不改代码来验证了，但是在这里我们可以利用泛型的特性另外做个小测试，
 * 将泛型去掉，并且将HttpServer中.addLast("aggregator", new HttpObjectAggregator(512 * 1024))
 * // 3这一行代码注释掉，然后观察注释前后的log。
 */
public class HttpHandler extends SimpleChannelInboundHandler<FullHttpRequest> {


    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, FullHttpRequest fullHttpRequest) throws Exception {
        System.out.println("class:" + fullHttpRequest.getClass().getName());

        /**
         * 生成response，这里使用的FullHttpResponse，同FullHttpRequest类似，通过这个我们就不用将response拆分成多个channel返回给请求端了。
         */
        DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_0,
                HttpResponseStatus.OK, Unpooled.wrappedBuffer("test".getBytes()));//2

        HttpHeaders headers = response.headers();
        headers.add("Content-Type", "application/json");
        /**
         * 添加header描述length。这一步是很重要的一步，如果没有这一步，
         * 你会发现用postman发出请求之后就一直在刷新，因为http请求方不知道返回的数据到底有多长
         */
        headers.add("Content-Length", response.content().readableBytes());//3
        headers.add("Connection", "keep-alive");

        channelHandlerContext.write(response);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelReadComplete");
        super.channelReadComplete(ctx);
        /**
         * channel读取完成之后需要输出缓冲流。如果没有这一步，你会发现postman同样会一直在刷新。
         */
        ctx.flush(); // 4
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("exceptionCaught");
        if (null != cause) cause.printStackTrace();
        if (null != ctx) ctx.close();
    }
}
