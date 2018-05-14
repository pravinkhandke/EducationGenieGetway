import { browser, protractor } from 'protractor';
import { NavBarPage } from './../../page-objects/jhi-page-objects';
import { UserReviewComponentsPage, UserReviewUpdatePage } from './user-review.page-object';

describe('UserReview e2e test', () => {
    let navBarPage: NavBarPage;
    let userReviewUpdatePage: UserReviewUpdatePage;
    let userReviewComponentsPage: UserReviewComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load UserReviews', () => {
        navBarPage.goToEntity('user-review');
        userReviewComponentsPage = new UserReviewComponentsPage();
        expect(userReviewComponentsPage.getTitle()).toMatch(/educationGenieApp.userReview.home.title/);
    });

    it('should load create UserReview page', () => {
        userReviewComponentsPage.clickOnCreateButton();
        userReviewUpdatePage = new UserReviewUpdatePage();
        expect(userReviewUpdatePage.getPageTitle()).toMatch(/educationGenieApp.userReview.home.createOrEditLabel/);
        userReviewUpdatePage.cancel();
    });

    it('should create and save UserReviews', () => {
        userReviewComponentsPage.clickOnCreateButton();
        userReviewUpdatePage.setRaitingInput('5');
        expect(userReviewUpdatePage.getRaitingInput()).toMatch('5');
        userReviewUpdatePage.setReviewInput('review');
        expect(userReviewUpdatePage.getReviewInput()).toMatch('review');
        userReviewUpdatePage.setCreateByInput('createBy');
        expect(userReviewUpdatePage.getCreateByInput()).toMatch('createBy');
        userReviewUpdatePage.setCreatedTimeInput('01/01/2001' + protractor.Key.TAB + '02:30AM');
        expect(userReviewUpdatePage.getCreatedTimeInput()).toContain('2001-01-01T02:30');
        userReviewUpdatePage.setUpdatedByInput('updatedBy');
        expect(userReviewUpdatePage.getUpdatedByInput()).toMatch('updatedBy');
        userReviewUpdatePage.setUpdatedTimeInput('01/01/2001' + protractor.Key.TAB + '02:30AM');
        expect(userReviewUpdatePage.getUpdatedTimeInput()).toContain('2001-01-01T02:30');
        userReviewUpdatePage.save();
        expect(userReviewUpdatePage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});
