package io.github.jumperonjava.blockatlas.api.blockatlas;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import io.github.jumperonjava.blockatlas.api.Server;
import net.minecraft.text.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.function.Supplier;

import static io.github.jumperonjava.blockatlas.api.motd.PingWithCache.FAILED_TEXT;

public class BlockAtlasServer implements Server {
    private String id;
    private String server_name;
    private int player_count;
    private int max_players;
    private String server_ip;
    private String server_port;
    private List<String> motd;
    private String server_slug;
    private String server_url;
    private String favicon_url;
    private String tags;
    private int rank;
    private boolean featured;

    @Override
    public String id() {
        return id;
    }

    @Override
    public Supplier<Text> motd() {
        return ()-> {
            var motd = Server.super.motd().get();
            if (motd.equals(FAILED_TEXT)) {
                var t = Text.empty();
                if(this.motd==null)
                    return FAILED_TEXT;
                for (var t2 : this.motd) {
                    t2 = t2.replaceAll("§#*[0-9A-F]{6}","");
                    t2 = t2.replaceAll("<#*[0-9A-F]{6}>","");
                    t.append(t2);
                }
                return t;
            }
            return motd;
        };
    }

    @Override
    public String server_name() {
        return server_name;
    }

    @Override
    public int player_count() {
        return player_count;
    }

    @Override
    public int max_players() {
        return max_players;
    }

    @Override
    public String server_ip() {
        return server_ip;
    }

    @Override
    public String server_port() {
        return server_port;
    }

    @Override
    public String server_slug() {
        return server_slug;
    }

    @Override
    public String server_url() {
        return server_url;
    }

    @Override
    public String favicon_url() {
        return favicon_url;
    }

    @Override
    public String tags() {
        return tags;
    }

    @Override
    public int rank() {
        return rank;
    }

    @Override
    public boolean featured() {
        return featured;
    }

    private static ThreadPoolExecutor POST_ASYNC = new ScheduledThreadPoolExecutor(4, (new ThreadFactoryBuilder()).setNameFormat("Record server add #%d").setDaemon(true).build());;
    @Override
    public void onConnected() {
        POST_ASYNC.submit(()->{
            try {
                String url = "https://blockatlas.net/scripts/record_copy_count.php";
                String body = "server_id=" + id();

                HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                connection.setDoOutput(true);

                try (OutputStream os = connection.getOutputStream()) {
                    byte[] input = body.getBytes(StandardCharsets.UTF_8);
                    os.write(input, 0, input.length);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void onAdded() {
        onConnected();
    }

    @Override
    public void voteAction() {

    }

    @Override
    public String getVoteLink() {
        return server_url()+"/vote";
    }
}
