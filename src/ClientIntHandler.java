import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.logging.Logger;


public class ClientIntHandler  extends ChannelInboundHandlerAdapter {


    private static Logger logger = Logger.getLogger(ClientIntHandler.class.getSimpleName());


    private  ChannelHandlerContext ctx;

    // 接收server端的消息，并打印出来
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        logger.info("HelloClientIntHandler.channelRead");
        ByteBuf result = (ByteBuf) msg;
        byte[] result1 = new byte[result.readableBytes()];
        result.readBytes(result1);
        System.out.println("收到服务器数据:" + new String(result1));
        result.release();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        logger.info("服务器断开");
    }

    // 连接成功后，向server发送消息
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        this.ctx=ctx;
        logger.info("服务器连接成功");



    }

    public void sendMessage(String message){

        ByteBuf encoded = ctx.alloc().buffer(4 * message.length());
        encoded.writeBytes(message.getBytes());
        ctx.write(encoded);
        ctx.flush();
    }

}
