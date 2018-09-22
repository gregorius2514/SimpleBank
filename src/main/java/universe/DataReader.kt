package universe

import java.util.*

interface DataReader<T> {
    
    //TODO Change `Object` to Interface
    fun readData() : List<T>
}