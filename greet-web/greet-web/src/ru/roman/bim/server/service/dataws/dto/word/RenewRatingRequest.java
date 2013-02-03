package ru.roman.bim.server.service.dataws.dto.word;

import ru.roman.bim.server.service.dataws.dto.AbstractRequest;

/** @author Roman 26.01.13 3:53 */
public class RenewRatingRequest extends AbstractRequest {

    private Long id;
    private Integer rating;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }
}
