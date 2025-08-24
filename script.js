@@ .. @@
   });

-  // Subtle parallax for hero orb
-  const orb = document.querySelector('.orb3d');
-  if (orb) {
-    let rafId = 0;
-    function onPointerMove(e) {
-      if (rafId) cancelAnimationFrame(rafId);
-      rafId = requestAnimationFrame(() => {
-        const rect = orb.getBoundingClientRect();
-        const dx = (e.clientX - (rect.left + rect.width / 2)) / rect.width;
-        const dy = (e.clientY - (rect.top + rect.height / 2)) / rect.height;
-        const rx = dy * -10;
-        const ry = dx * 10;
-        orb.style.transform = `rotateX(${18 + rx}deg) rotateY(${-18 + ry}deg)`;
-      });
-    }
-    window.addEventListener('mousemove', onPointerMove, { passive: true });
-  }
 })();