package com.github.partition.nonconfscope.repos;

import android.support.annotation.Nullable;
import android.util.Log;

import com.github.partition.nonconfscope.dagger.NonConfigurationScope;
import com.github.partition.nonconfscope.rx.ObserverAdapter;
import com.github.partition.nonconfscope.rx.RxUtils;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.disposables.Disposables;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.BehaviorSubject;


@NonConfigurationScope
public class ReposPresenter {

    private final BehaviorSubject<List<Repo>> repos = BehaviorSubject.create();
    //  private Subscription disposable = Subscriptions.empty();
    private Disposable disposable = Disposables.empty();

    @Inject
    ReposPresenter(GithubService githubService) {
        githubService
                .getRepos()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(RxUtils.muteOnComplete(repos));
    }

    void setView(@Nullable final ReposView view) {
        disposable.dispose();
        if (view != null) {
            subscribeView(view);
        }
    }

    private void subscribeView(final ReposView view) {
//    disposable =
        Observable<List<Repo>> listObservable = repos.observeOn(Schedulers.trampoline());
        listObservable
                .subscribe(new ObserverAdapter<List<Repo>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable = d;
                        super.onSubscribe(d);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("ReposPresenter", "Error", e);
                        view.displayError();
                    }

                    @Override
                    public void onNext(List<Repo> repos) {
                        view.setRepos(repos);
                    }
                });
    }
}
