package com.github.partition.nonconfscope.rx;

//import rx.Observer;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

public abstract class RxUtils {

    private RxUtils() {

    }

    public static <T> Observer<T> muteOnComplete(final Observer<T> observer) {
        return new ObserverAdapter<T>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                observer.onSubscribe(d);
            }

            @Override
            public void onComplete() {
                observer.onComplete();
            }
        };
    }
}
