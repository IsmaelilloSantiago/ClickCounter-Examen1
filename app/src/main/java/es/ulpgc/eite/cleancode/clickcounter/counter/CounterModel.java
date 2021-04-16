package es.ulpgc.eite.cleancode.clickcounter.counter;

import android.util.Log;

public class CounterModel implements CounterContract.Model {

  public static String TAG = CounterModel.class.getSimpleName();

  private String data;
  private String clicks;

  public CounterModel(String data, String clicks) {

    this.data = data;
    this.clicks = clicks;
  }

  @Override
  public String aumentarCounter( String data){
    int a = Integer.parseInt(data);
    if(a < 9 ){
      a ++;
      this.data = a + "";

    }else{
      this.data = 0 + "";

    }
    return  this.data;

  }


  @Override
  public String getStoredData() {
    // Log.e(TAG, "getStoredData()");
    return data;
  }
  @Override
  public String getClicks() {
    // Log.e(TAG, "getStoredData()");
    return clicks;
  }

  @Override
  public String aumentarClicks( String data){
    int a = Integer.parseInt(data);
      a ++;
      this.clicks = a + "";
       return  this.clicks;

  }

  @Override
  public void onRestartScreen(String data) {
    // Log.e(TAG, "onRestartScreen()");
  }

  @Override
  public void onDataFromNextScreen(String data) {
    // Log.e(TAG, "onDataFromNextScreen()");
  }

  @Override
  public void onDataFromPreviousScreen(String data) {
    // Log.e(TAG, "onDataFromPreviousScreen()");
  }
}
