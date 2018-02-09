package com.example.dagger.sample1;

import dagger.Component;
import dagger.Subcomponent;

/**
 * Created by Administrator on 2018/2/8.
 */
@Component(modules = CarModel.class)
public interface CarComponent {

//    void inject(BaseActivity activity);
    void inject(Car car);

//    Engine getEngine();
//    Seat getSeat();
//    Wheel getWheel();
}
