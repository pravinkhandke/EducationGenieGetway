import { browser, protractor } from 'protractor';
import { NavBarPage } from './../../page-objects/jhi-page-objects';
import { ContentReviewComponentsPage, ContentReviewUpdatePage } from './content-review.page-object';

describe('ContentReview e2e test', () => {
    let navBarPage: NavBarPage;
    let contentReviewUpdatePage: ContentReviewUpdatePage;
    let contentReviewComponentsPage: ContentReviewComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load ContentReviews', () => {
        navBarPage.goToEntity('content-review');
        contentReviewComponentsPage = new ContentReviewComponentsPage();
        expect(contentReviewComponentsPage.getTitle()).toMatch(/educationGenieApp.contentReview.home.title/);
    });

    it('should load create ContentReview page', () => {
        contentReviewComponentsPage.clickOnCreateButton();
        contentReviewUpdatePage = new ContentReviewUpdatePage();
        expect(contentReviewUpdatePage.getPageTitle()).toMatch(/educationGenieApp.contentReview.home.createOrEditLabel/);
        contentReviewUpdatePage.cancel();
    });

    it('should create and save ContentReviews', () => {
        contentReviewComponentsPage.clickOnCreateButton();
        contentReviewUpdatePage.setRaitingInput('5');
        expect(contentReviewUpdatePage.getRaitingInput()).toMatch('5');
        contentReviewUpdatePage.setReviewInput('review');
        expect(contentReviewUpdatePage.getReviewInput()).toMatch('review');
        contentReviewUpdatePage.setCreateByInput('createBy');
        expect(contentReviewUpdatePage.getCreateByInput()).toMatch('createBy');
        contentReviewUpdatePage.setCreatedTimeInput('01/01/2001' + protractor.Key.TAB + '02:30AM');
        expect(contentReviewUpdatePage.getCreatedTimeInput()).toContain('2001-01-01T02:30');
        contentReviewUpdatePage.setUpdatedByInput('updatedBy');
        expect(contentReviewUpdatePage.getUpdatedByInput()).toMatch('updatedBy');
        contentReviewUpdatePage.setUpdatedTimeInput('01/01/2001' + protractor.Key.TAB + '02:30AM');
        expect(contentReviewUpdatePage.getUpdatedTimeInput()).toContain('2001-01-01T02:30');
        contentReviewUpdatePage.contentSelectLastOption();
        contentReviewUpdatePage.save();
        expect(contentReviewUpdatePage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});
