package com.mySpringBoot.test.enumtest;

public enum EnumTest {
	//MON, TUE, WED, THU, FRI, SAT, SUN;
	
	//自定义属性和方法
	MON(1), TUE(2), WED(3), THU(4), FRI(5), SAT(6) {
        @Override
        public boolean isRest() {
            return true;
        }
    },
    SUN(0) {
        @Override
        public boolean isRest() {
            return true;
        }
    };
 
    private int value;
 
    private EnumTest(int value) {
        this.value = value+1;
    }
 
    public int getValue() {
        return value;
    }
 
    public boolean isRest() {
        return false;
    }
    
    /**抽象方法，由不同的枚举值提供不同的实现。
     * @param x
     * @param y
     * @return
     */
    //public abstract double eval(double x, double y);
}
