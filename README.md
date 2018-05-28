# GenNotes

Uses: 
1. ViewModels from android.arch.lifecycle.ViewModel, 
2. LiveData from android.arch.lifecycle.LiveData<T>, 
3. Room Libary, DAO(Data Access Objects)
4. Butterknife libary

GenNotes is a generic note taking app created to study the Artchitectural Components in Android system. 
David Gassner's course on LinkedIn Learning demonstrating the process was very useful for building this app.
This project is built using MVVM architecture. 
Each activity (View) has it's own ViewModel that in turn talks to a Repository (best practice, not an architectural component in
standard MVVM). Repository has the sole knowledge of where the data comes from but it knows nothing about the business logic.
The business logic is encapsulated in the ViewModels.
Room database library is used for persistent data storage. Repository deals with DAO (Data Access Objects) interface exposed
to make persistent changes. Room libary returns Live data objects for notes. The UI "reacts" to any chnages made to Live data
objects. For UI, finding and instantiating Views is done using Butterknife library. 
