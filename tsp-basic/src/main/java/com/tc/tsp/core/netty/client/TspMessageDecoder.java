package com.tc.tsp.core.netty.client;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * 
 * @author jon.liang
 * 
 */
public final class TspMessageDecoder extends ByteToMessageDecoder {

	@Override
	public void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {

		// length: i32
		// stx
		// data
		// etx
		if (in.readableBytes() < 4) { // length: i32
			return;
		}

		int begin = in.readerIndex();
		int length = in.readInt();
		if (in.readableBytes() < length) { // STX - ETX
			in.readerIndex(begin);
			return;
		}
		in.readerIndex(begin + 4 + length);

		ByteBuf message = in.slice(begin, 4 + length); //
		message.retain();

		out.add(message);
	}

}