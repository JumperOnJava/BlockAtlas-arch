package io.github.jumperonjava.blockatlas.api;

import io.github.jumperonjava.blockatlas.api.motd.PingWithCache;
import net.minecraft.text.Text;

import java.util.function.Supplier;

public interface Server {
    public String id();
    public String server_name();
    public int player_count();
    public int max_players();
    public String server_ip();
    public String server_port();
    default Supplier<Text> motd() {
        return () -> PingWithCache.getCachedMotd(server_ip());
    }
    public String server_slug();
    public String server_url();
    public String favicon_url();
    public String tags();
    public int rank();
    public boolean featured();

    public void onConnected();
    public void onAdded();

    public void voteAction();

    String getVoteLink();

    String getPostReq();
}
