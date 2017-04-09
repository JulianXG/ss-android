package cn.kalyter.ss.contract;

import java.util.List;

import cn.kalyter.ss.common.BasePresenter;
import cn.kalyter.ss.common.BaseView;
import cn.kalyter.ss.model.Microblog;
import cn.kalyter.ss.model.OneLineWrapper;
import cn.kalyter.ss.util.OneLineRecyclerAdapter;

/**
 * Created by Kalyter on 2017-4-2 0002.
 */

public interface MicroblogDetailContract {
    interface View extends BaseView {
        long getMicroblogId();

        void showLoadDetailError();

        void showMicroblog(Microblog microblog);

        void showCommentsCount(long count);

        void showRepostsCount(long count);

        void setCommentAdapter(OneLineRecyclerAdapter adapter);

        void setRepostAdapter(OneLineRecyclerAdapter adapter);
    }

    interface Presenter extends BasePresenter {
        void loadMicroblog(long microblogId);

        void loadReposts(long microblogId);

        void loadComments(long microblogId);

        void loadLikes(long microblogId);
    }
}
