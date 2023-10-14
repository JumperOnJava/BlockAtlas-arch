package io.github.jumperonjava.blockatlas.api.blockatlas;

import java.util.*;
public class BlockAtlasApiResponse {
    public int current_page;
    public int total_pages;
    public List<BlockAtlasServer> servers;
    public String total_servers;
    public List<String> unique_tags;
}
