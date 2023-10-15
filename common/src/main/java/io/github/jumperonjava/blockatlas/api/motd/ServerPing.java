package io.github.jumperonjava.blockatlas.api.motd;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.sun.jna.platform.win32.WinBase;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerServerListWidget;
import net.minecraft.client.network.MultiplayerServerListPinger;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.server.ServerMetadata;
import net.minecraft.util.logging.UncaughtExceptionLogger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Text;

import java.util.Map;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.function.Consumer;

public class ServerPing{
    public MultiplayerServerListPinger pinger = new MultiplayerServerListPinger();
    public Consumer<ServerInfo> finish;
    private ServerInfo server;
    private static ThreadPoolExecutor PING_POOL = new ScheduledThreadPoolExecutor(16, (new ThreadFactoryBuilder()).setNameFormat("Server Pinger #%d").setDaemon(true).build());;
    public static void ping(String address,Consumer<ServerInfo> onFinish){
        ping(address,onFinish,()->{});
    }

    /**
     * @param address
     * @param onFinish
     */
    public static void ping(String address,Consumer<ServerInfo> onFinish,Runnable onFail) {
        ping(address,onFinish,onFail,5);
    }
    public static void ping(String address,Consumer<ServerInfo> onFinish,Runnable onFail, int triesLeft){
        if(triesLeft==0)
            return;
        var serverinfo = new ServerInfo("",address, false);

        var ping = new ServerPing();
        ping.finish = onFinish;
        ping.server = serverinfo;
        PING_POOL.submit(() -> {
            try {
                ping.pinger.add(serverinfo, ping::save);
                new Timer().schedule(new TimerTask() {
                    public void run() {
                        ping.pinger.cancel();
                        ping(address, onFinish, onFail, triesLeft-1);
                    }
                }, 5000);
            } catch (Exception e) {
                onFail.run();
            }
        });
    }

    private void save() {
        //server.players=null;
        server.setResourcePackPolicy(ServerInfo.ResourcePackPolicy.ENABLED);
        server.setIcon(null);
        finish.accept(server);
    }
}
