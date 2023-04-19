package com.rentminder

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.rentminder.dto.Bill
import com.rentminder.dto.Members
class MainViewModel() : ViewModel() {
    var bills : MutableLiveData<List<Bill>> = MutableLiveData()
    var allBills : MutableLiveData<List<Bill>> = MutableLiveData()
    var members : MutableLiveData<List<Members>> = MutableLiveData()
    var member: Members? = null

    private lateinit var firestore : FirebaseFirestore

    init {
        firestore = FirebaseFirestore.getInstance()
        firestore.firestoreSettings = FirebaseFirestoreSettings.Builder().build()
        listenToBills()
    }

    //Function to get the bills of the current member
    fun listenToBills() {
        member?.let {
            member ->
            firestore.collection("Members").document(member.uid).collection("Payments").addSnapshotListener {
                    snapshot, e ->
                if (e != null) {
                    Log.w("Listen failed", e)
                    return@addSnapshotListener
                }
                snapshot?.let {
                    val allBills = ArrayList<Bill>()
                    val documents = snapshot.documents

                    documents.forEach {
                        val bill = it.toObject(Bill::class.java)
                        bill?.let {
                            allBills.add(bill)
                        }
                    }
                    bills.value = allBills
                }
            }
        }
    }

    //Function used to get the bills from all members
    fun listenToAllBills() {
        firestore.collection("Members").addSnapshotListener { snapshot, e ->
            if (e != null) {
                Log.w("Listen failed", e)
                return@addSnapshotListener
            }
            snapshot?.let { membersSnapshot ->
                val bills = ArrayList<Bill>()
                membersSnapshot.documents.forEach { memberDoc ->
                    val paymentsRef = memberDoc.reference.collection("Payments")
                    paymentsRef.addSnapshotListener { paymentsSnapshot, e ->
                        if (e != null) {
                            Log.w("Listen failed", e)
                            return@addSnapshotListener
                        }
                        paymentsSnapshot?.let {
                            it.documents.forEach { billDoc ->
                                val bill = billDoc.toObject(Bill::class.java)
                                bill?.let {
                                    bills.add(bill)
                                }
                            }
                            allBills.value = bills
                        }
                    }
                }
            }
        }
    }

    //Gets the members from Firebase
    fun listenToMembers() {
        firestore.collection("Members").addSnapshotListener { snapshot, e ->
            if (e != null) {
                Log.w("Listen failed", e)
                return@addSnapshotListener
            }
            snapshot?.let {
                val allMembers = ArrayList<Members>()
                val documents = snapshot.documents

                documents.forEach {
                    val member = it.toObject(Members::class.java)
                    member?.let {
                        allMembers.add(member)
                    }
                }
                members.value = allMembers
            }
        }
    }

    //Saves or Updates the Bill in Firebase
    fun saveBill(selectedBill: Bill) {
        member?.let { member ->
            val collectionRef = firestore.collection("Members").document(member.uid).collection("Payments")
            val documentRef = if (selectedBill.billId == null || selectedBill.billId.isEmpty()) {
                collectionRef.document()
            } else {
                collectionRef.document(selectedBill.billId)
            }
            selectedBill.billId = documentRef.id
            val handle = documentRef.set(selectedBill)
            handle.addOnSuccessListener { Log.d("Firebase", "Document saved") }
            handle.addOnFailureListener { Log.e("Firebase", "Save failed $it") }
        }
    }

    //Saves the member to Firebase
    fun saveMember(){
        member?.let {
            member ->
            val handle = firestore.collection("Members").document(member.uid).set(member)
            handle.addOnSuccessListener { Log.d("Firebase", "Document saved") }
            handle.addOnFailureListener { Log.e("Firebase", "Save failed $it") }
        }
    }

    //Deletes a bill from Firebase
    fun delete(bill: Bill) {
        member?.let{
            member ->
            var billCollection = firestore.collection("Members").document(member.uid).collection("Payments").document(bill.billId)
            billCollection.delete()
                .addOnSuccessListener {
                    // The document was successfully deleted
                    Log.d("TAG", "DocumentSnapshot successfully deleted!")
                }
                .addOnFailureListener { e ->
                    // There was an error deleting the document
                    Log.w("TAG", "Error deleting document", e)
                }
        }
    }
}