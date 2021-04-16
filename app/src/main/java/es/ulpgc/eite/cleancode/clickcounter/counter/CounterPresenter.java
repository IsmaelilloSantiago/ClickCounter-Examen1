package es.ulpgc.eite.cleancode.clickcounter.counter;

import android.util.Log;

import java.lang.ref.WeakReference;

import es.ulpgc.eite.cleancode.clickcounter.app.AppMediator;
import es.ulpgc.eite.cleancode.clickcounter.app.ClicksToCounterState;
import es.ulpgc.eite.cleancode.clickcounter.app.CounterToClicksState;

public class CounterPresenter implements CounterContract.Presenter {

  public static String TAG = CounterPresenter.class.getSimpleName();

  private WeakReference<CounterContract.View> view;
  private CounterState state;
  private CounterContract.Model model;

  private AppMediator mediator;

  public CounterPresenter(AppMediator mediator) {
    this.mediator = mediator;
    state = mediator.getCounterState();
  }


  @Override
  public void onStart() {
    // Log.e(TAG, "onStart()");

    // initialize the state if is necessary
    if (state == null) {
      state = new CounterState();
    }

    // call the model and update the state
    state.data = model.getStoredData();
    state.resetEnable = false;
    state.clickEnable = false;

    /*
    // use passed state if is necessary
    PreviousToCounterState savedState = getStateFromPreviousScreen();
    if (savedState != null) {

      // update the model if is necessary
      model.onDataFromPreviousScreen(savedState.data);

      // update the state if is necessary
      state.data = savedState.data;
    }
    */
  }

  @Override
  public void onRestart() {
    // Log.e(TAG, "onRestart()");

    // update the model if is necessary
    model.onRestartScreen(state.data);
  }

  @Override
  public void onResume() {
    Log.e(TAG, "onResume()");

    // use passed state if is necessary
    ClicksToCounterState savedState = getStateFromNextScreen();

    if (savedState != null) {
        if(savedState.reseteado){
          Log.e(TAG, "entra");
          state.clicks = model.resetearClicks();
        }
      // update the model if is necessary
      //model.onDataFromNextScreen(savedState.data);

      // update the state if is necessary
      //state.data = savedState.data;
    }

    // call the model and update the state
    //state.data = model.getStoredData();

    // update the view
    view.get().onDataUpdated(state);

  }


  @Override
  public void onBackPressed() {
    // Log.e(TAG, "onBackPressed()");
  }

  @Override
  public void onPause() {
    // Log.e(TAG, "onPause()");
  }

  @Override
  public void onDestroy() {
    // Log.e(TAG, "onDestroy()");
  }

  @Override
  public void onClicksPressed() {
    Log.e(TAG, "onClicksPressed()");



    CounterToClicksState estado = new CounterToClicksState();
    estado.data = state.clicks;
    Log.e(TAG, estado.data);
    passStateToNextScreen(estado);
    view.get().navigateToNextScreen();

  }

  @Override
  public void onResetPressed() {
    Log.e(TAG, "onResetPressed()");
    state.data = model.resetearCuenta();
    //state.data = 0 + "";
    state.resetEnable = false;
    view.get().onDataUpdated(state);

    //onResume();

  }

  @Override
  public void onIncrementPressed() {

    if(!state.data.equals("" + 9)){
      state.resetEnable = true;
      state.clickEnable = true;
    }else{
      state.resetEnable = false;
    }


    Log.e(TAG, "onIncrementPressed()");
    String dato = model.getStoredData();
    Log.e(TAG, dato);
    String clicks = model.getClicks();
    Log.e(TAG, clicks);


    state.clicks = model.aumentarClicks(clicks);
    state.data = model.aumentarCounter(dato);

    onResume();

  }

  private void passStateToNextScreen(CounterToClicksState state) {
    mediator.setCounterNextScreenState(state);
  }

  private ClicksToCounterState getStateFromNextScreen() {
    return mediator.getCounterNextScreenState();
  }

  @Override
  public void injectView(WeakReference<CounterContract.View> view) {
    this.view = view;
  }

  @Override
  public void injectModel(CounterContract.Model model) {
    this.model = model;
  }

}
