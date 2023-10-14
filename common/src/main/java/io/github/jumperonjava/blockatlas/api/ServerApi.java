package io.github.jumperonjava.blockatlas.api;
import java.util.*;
public interface ServerApi {
    void setTagHandler(ListHandler<Tag> serverList);
    List<Tag> getTags();
    Tag getMainTag();
}
