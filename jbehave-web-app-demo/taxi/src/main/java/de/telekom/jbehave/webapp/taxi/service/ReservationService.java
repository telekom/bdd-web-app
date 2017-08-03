package de.telekom.jbehave.webapp.taxi.service;

import de.telekom.jbehave.webapp.taxi.domain.Reservation;
import de.telekom.jbehave.webapp.taxi.domain.User;
import de.telekom.jbehave.webapp.taxi.repositories.ReservationRepository;
import de.telekom.jbehave.webapp.taxi.repositories.UserRepository;
import de.telekom.jbehave.webapp.taxi.controller.vo.ReservationVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

import static org.springframework.beans.BeanUtils.copyProperties;

@Service
public class ReservationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Transactional
    public void saveReservation(String username, ReservationVO reservationVO) {
        User user = getUser(username);

        // only one reservation at a time
        reservationRepository.deleteByUser(user);

        Reservation reservation = new Reservation();
        copyProperties(reservationVO, reservation);
        reservation.setUser(user);
        reservation.setCreationDate(new Date());
        reservationRepository.save(reservation);
    }

    public ReservationVO getReservation(String username) {
        User user = getUser(username);
        List<Reservation> reservations = reservationRepository.findByUser(user);
        if (reservations.isEmpty()) {
            return null;
        }

        Reservation reservation = reservations.get(0);
        ReservationVO reservationVO = new ReservationVO();
        copyProperties(reservation, reservationVO);
        return reservationVO;
    }

    private User getUser(String username) {
        return userRepository.getByUsername(username);
    }

}
