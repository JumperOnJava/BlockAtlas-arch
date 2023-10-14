package io.github.jumperonjava.blockatlas.util;

/**
 * made for mixin/ServerInfoMixin
 */
public interface ServerInfoExt {
    default void setVoteLink(String s){}
    default String getVoteLink(){return null;}
    void setPostReq(String s);
    String getPostReq();
}
