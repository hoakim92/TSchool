function base_url() {
    var pathparts = location.pathname.split('/');
    return location.origin + '/' + pathparts[1].trim('/') + '/';
}
