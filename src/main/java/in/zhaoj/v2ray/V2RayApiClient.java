package in.zhaoj.v2ray;

import com.v2ray.core.app.log.command.LoggerServiceGrpc;
import com.v2ray.core.app.proxyman.command.HandlerServiceGrpc;
import com.v2ray.core.app.stats.command.StatsServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

/**
 * @version 1.0
 * @author glzjin
 */
public class V2RayApiClient {
    private static final Logger logger = Logger.getLogger(V2RayApiClient.class.getName());

    private final ManagedChannel channel;
    private final HandlerServiceGrpc.HandlerServiceBlockingStub handlerServiceBlockingStub;
    private final LoggerServiceGrpc.LoggerServiceBlockingStub loggerServiceBlockingStub;
    private final StatsServiceGrpc.StatsServiceBlockingStub statsServiceBlockingStub;


    public V2RayApiClient(String host, int port) {
        this(ManagedChannelBuilder.forAddress(host, port)
                // Channels are secure by default (via SSL/TLS). For the example we disable TLS to avoid
                // needing certificates.
                .usePlaintext()
                .build());
    }

    public V2RayApiClient(ManagedChannel channel) {
        this.channel = channel;
        this.handlerServiceBlockingStub = HandlerServiceGrpc.newBlockingStub(channel);
        this.loggerServiceBlockingStub = LoggerServiceGrpc.newBlockingStub(channel);
        this.statsServiceBlockingStub = StatsServiceGrpc.newBlockingStub(channel);
    }

    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    public HandlerServiceGrpc.HandlerServiceBlockingStub getHandlerServiceBlockingStub() {
        return handlerServiceBlockingStub;
    }

    public LoggerServiceGrpc.LoggerServiceBlockingStub getLoggerServiceBlockingStub() {
        return loggerServiceBlockingStub;
    }

    public StatsServiceGrpc.StatsServiceBlockingStub getStatsServiceBlockingStub() {
        return statsServiceBlockingStub;
    }
}
