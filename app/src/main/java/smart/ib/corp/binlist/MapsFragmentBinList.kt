package smart.ib.corp.binlist

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import smart.ib.corp.binlist.databinding.FragmentMapsBinListBinding

class MapsFragmentBinList : Fragment() {
    private lateinit var binding: FragmentMapsBinListBinding
    private val launcher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { map ->
            if (map.values.all { it } && map.isNotEmpty()) {
                startLocation()
            } else {
                findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
            }
        }
    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(p0: LocationResult) {
            //Log.d("my", p0.lastLocation.toString())
        }
    }

    private lateinit var fusedClient: FusedLocationProviderClient

    @SuppressLint("MissingPermission")
    private fun startLocation() {
        val request =
            LocationRequest.Builder(1000).setPriority(Priority.PRIORITY_HIGH_ACCURACY).build()
        fusedClient.requestLocationUpdates(
            request,
            locationCallback,
            Looper.getMainLooper()
        )
    }

    private fun checkPermissionLocation() {
        val isAllGranted = REQUEST_PERMISSI0NS.all { permission ->
            ContextCompat.checkSelfPermission(
                binding.root.context,
                permission
            ) == PackageManager.PERMISSION_GRANTED
        }
        if (isAllGranted) {
            startLocation()
        } else launcher.launch(REQUEST_PERMISSI0NS)
    }


    private val callback = OnMapReadyCallback { googleMap ->
        val mapsLocation = arguments?.getIntegerArrayList("maps_location")
        val marker = LatLng(mapsLocation?.get(0)!!.toDouble(), mapsLocation[1].toDouble())
        googleMap.addMarker(MarkerOptions().position(marker).title("Marker in Bank"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(marker))
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(marker, 15f))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMapsBinListBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fusedClient = LocationServices.getFusedLocationProviderClient(view.context)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }

    override fun onStart() {
        super.onStart()
        checkPermissionLocation()
    }

    override fun onStop() {
        super.onStop()
        fusedClient.removeLocationUpdates(locationCallback)
    }

    companion object {
        private val REQUEST_PERMISSI0NS: Array<String> = arrayOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
    }

}