package service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import model.Show;
import repository.ShowRepository;

@Service
public class ShowService {
	
	@Autowired
	ShowRepository showRepository;
	
	public Show createShow(int showNumber, int rows, int columns, int cancelWindowMinute) {
		Show show = new Show(showNumber, rows, columns, cancelWindowMinute);
		saveShow(show);
		return getShowByShowNumber(show.getShowNumber());
	}
	
	public Show getShowByShowNumber(int showNumber) {
		return showRepository
				.findAll()
				.stream()
				.filter(show -> show.getShowNumber() == showNumber)
				.findFirst()
				.orElse(null);
	}
	
	public void saveShow(Show show) {
		showRepository.flush();
		showRepository.save(show);
	}
}
