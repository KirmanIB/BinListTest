package smart.ib.corp.binlist.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import smart.ib.corp.binlist.R
import smart.ib.corp.binlist.databinding.FragmentMapsBinListBinding

class MapsFragmentBinList : Fragment() {
    private lateinit var binding: FragmentMapsBinListBinding

    private val callback = OnMapReadyCallback { googleMap ->
        val mapsLocation = arguments?.getIntegerArrayList("maps_location")
        val marker =
            LatLng(mapsLocation?.get(LATITUDE)!!.toDouble(), mapsLocation[LONGITUDE].toDouble())
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
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }

    companion object {
        private const val LATITUDE = 0
        private const val LONGITUDE = 1
    }

}