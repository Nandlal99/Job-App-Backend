package com.nandlal.jobapp.Review.Imp;

import com.nandlal.jobapp.Company.Company;
import com.nandlal.jobapp.Company.CompanyService;
import com.nandlal.jobapp.Review.Review;
import com.nandlal.jobapp.Review.ReviewRepository;
import com.nandlal.jobapp.Review.ReviewService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImp implements ReviewService {

    private ReviewRepository reviewRepository;
    private CompanyService companyService;

    public ReviewServiceImp(ReviewRepository reviewRepository, CompanyService companyService) {
        this.reviewRepository = reviewRepository;
        this.companyService = companyService;
    }

    @Override
    public List<Review> getAllReviews(Long companyId) {
        List<Review> reviews = reviewRepository.findByCompanyId(companyId);
        return reviews;
    }

    @Override
    public boolean createReview(Long companyId, Review review) {
        Company company = companyService.getCompanyById(companyId);
        if(company != null){
            review.setCompany(company);
            reviewRepository.save(review);
            return true;
        }
        return false;
    }

    @Override
    public Review getReviewById(Long companyId, Long reviewId) {
        List<Review> reviews = reviewRepository.findByCompanyId(companyId);
        return reviews.stream()
                .filter(review -> review.getId().equals(reviewId))
                .findFirst()
                .orElse(null);
    }

    @Override
    public boolean updateReviewById(Long companyId, Long reviewId, Review updateReview) {
        List<Review> reviews = reviewRepository.findByCompanyId(companyId);
        if(reviews != null){
            for (Review review: reviews){
                if (review.getId().equals(reviewId)){
                    review.setTitle(updateReview.getTitle());
                    review.setDescription(updateReview.getDescription());
                    review.setRating(updateReview.getRating());
                    reviewRepository.save(review);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean deleteReviewById(Long companyId, Long reviewId) {
        List<Review> reviews = reviewRepository.findByCompanyId(companyId);
        if(reviews != null){
            Review review = reviewRepository.findById(reviewId).orElse(null);
            if(review != null){
                Company company = review.getCompany();
                company.getReviews().remove(review);
                companyService.updateCompany(company,companyId);
                reviewRepository.deleteById(reviewId);
                return true;
            }
        }
        return false;
    }


}
