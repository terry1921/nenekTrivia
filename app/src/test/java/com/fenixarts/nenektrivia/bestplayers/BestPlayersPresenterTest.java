package com.fenixarts.nenektrivia.bestplayers;

import com.fenixarts.nenektrivia.TestUseCaseScheduler;
import com.fenixarts.nenektrivia.UseCaseHandler;
import com.fenixarts.nenektrivia.bestplayers.domain.model.BestPlayersItem;
import com.fenixarts.nenektrivia.bestplayers.domain.usecases.GetBestPlayerList;
import com.fenixarts.nenektrivia.data.source.DataSource.LoadBestPlayerCallback;
import com.fenixarts.nenektrivia.data.source.Repository;
import com.google.common.collect.Lists;

import org.junit.Before;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

/**
 * NenekTrivia
 * Created by terry0022 on 06/03/18 - 17:55.
 */
public class BestPlayersPresenterTest {

    private List<BestPlayersItem> BEST_PLAYERS;

    @Mock
    private Repository mRepository;

    @Mock
    private BestPlayersContract.View mBestPlayersView;

    @Captor
    private ArgumentCaptor<LoadBestPlayerCallback> mLoadBestPlayerCallback;

    private BestPlayersPresenter mBestPlayerPresenter;

    @Before
    private void setUpBestPlayerPresenter(){
        // Mockito has a very convenient way to inject mocks by using the @Mock annotation. To
        // inject the mocks in the test the initMocks method needs to be called.
        MockitoAnnotations.initMocks(this);

        // Get a reference to the class under test
        mBestPlayerPresenter = givenBestPlayerPresenter();

        // The presenter won't update the view unless it's active.
        when(mBestPlayersView.isActive()).thenReturn(true);

        // We start the tasks to 3, with one active and two completed
        //TASKS = Lists.newArrayList(new Task("Title1", "Description1"),
        //new Task("Title2", "Description2", true), new Task("Title3", "Description3", true));
        BEST_PLAYERS = Lists.newArrayList(
                new BestPlayersItem("UJw9023eujd09u32jewmdc","","Enrique De Jesus",22),
                new BestPlayersItem("UJw9023eujd09u3298ikjh","","Hector Armando",20),
                new BestPlayersItem("UJw9023eujd09u32poijns","","Salvador Landaverde",7));

    }

    private BestPlayersPresenter givenBestPlayerPresenter() {
        UseCaseHandler useCaseHandler = new UseCaseHandler(new TestUseCaseScheduler());
        GetBestPlayerList useCase = new GetBestPlayerList(mRepository);
        return new BestPlayersPresenter(mBestPlayersView,useCaseHandler,useCase);
    }

}