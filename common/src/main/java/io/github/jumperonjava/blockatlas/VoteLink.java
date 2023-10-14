package io.github.jumperonjava.blockatlas;

/**
 * made for mixin/ServerInfoMixin
 */
public interface VoteLink {
    default void setVoteLink(String s){}
    default String getVoteLink(){return null;}
}
