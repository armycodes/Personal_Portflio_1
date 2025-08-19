(function () {
  const MIN_FADE_OPACITY = 0.15;
  const sections = Array.from(document.querySelectorAll('.section.fade-on-out'));
  const navLinks = Array.from(document.querySelectorAll('.site-nav .nav-link'));
  const yearEl = document.getElementById('year');
  const themeToggle = document.getElementById('themeToggle');

  if (yearEl) yearEl.textContent = String(new Date().getFullYear());

  // Reveal on scroll
  const revealEls = Array.from(document.querySelectorAll('.reveal'));
  const revealObserver = new IntersectionObserver(
    (entries) => {
      entries.forEach((entry) => {
        if (entry.isIntersecting) {
          entry.target.classList.add('is-visible');
          // Optionally stop observing once revealed
          revealObserver.unobserve(entry.target);
        }
      });
    },
    { rootMargin: '0px 0px -10% 0px', threshold: 0.1 }
  );
  revealEls.forEach((el) => revealObserver.observe(el));

  // Fade out sections that have been scrolled past
  let ticking = false;
  function onScroll() {
    if (!ticking) {
      window.requestAnimationFrame(() => {
        sections.forEach((section) => {
          const rect = section.getBoundingClientRect();
          let opacity = 1;
          if (rect.top < 0) {
            const amount = Math.min(1, Math.abs(rect.top) / window.innerHeight);
            opacity = Math.max(MIN_FADE_OPACITY, 1 - amount);
          }
          section.style.setProperty('--section-opacity', String(opacity));
        });
        ticking = false;
      });
      ticking = true;
    }
  }
  window.addEventListener('scroll', onScroll, { passive: true });
  window.addEventListener('resize', onScroll);
  onScroll();

  // Highlight active nav link
  const sectionById = new Map(sections.map((s) => [s.id, s]));
  const navByHash = new Map(navLinks.map((a) => [a.getAttribute('href') || '', a]));
  const activeObserver = new IntersectionObserver(
    (entries) => {
      entries.forEach((entry) => {
        if (entry.isIntersecting) {
          const id = '#' + entry.target.id;
          navLinks.forEach((a) => a.classList.remove('active'));
          const link = navByHash.get(id);
          if (link) link.classList.add('active');
        }
      });
    },
    { threshold: 0.35 }
  );
  sections.forEach((s) => activeObserver.observe(s));

  // Theme cycling
  const themes = ['default', 'sunset', 'aurora'];
  function getNextTheme(current) {
    const idx = themes.indexOf(current);
    return themes[(idx + 1) % themes.length];
  }
  function applyTheme(theme) {
    if (theme === 'default') {
      document.documentElement.removeAttribute('data-theme');
    } else {
      document.documentElement.setAttribute('data-theme', theme);
    }
    try { localStorage.setItem('theme', theme); } catch {}
  }
  const savedTheme = (() => { try { return localStorage.getItem('theme'); } catch { return null; } })();
  if (savedTheme && themes.includes(savedTheme)) applyTheme(savedTheme);
  if (themeToggle) {
    themeToggle.addEventListener('click', () => {
      const current = document.documentElement.getAttribute('data-theme') || 'default';
      applyTheme(getNextTheme(current));
    });
  }
})();

