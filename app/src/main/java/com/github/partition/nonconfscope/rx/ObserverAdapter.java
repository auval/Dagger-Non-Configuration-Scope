package com.github.partition.nonconfscope.rx;

//import rx.Observer;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

public abstract class ObserverAdapter<T> implements Observer<T> {

  @Override
  public void onSubscribe(@NonNull Disposable d) {

  }

  @Override
  public void onComplete() {

  }

  @Override
  public void onError(Throwable e) {

  }

  @Override
  public void onNext(T t) {

  }
}
