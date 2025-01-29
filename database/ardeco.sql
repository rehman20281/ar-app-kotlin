-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: Jan 29, 2025 at 10:18 AM
-- Server version: 10.11.10-MariaDB
-- PHP Version: 7.2.34

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `u730769424_ar`
--

-- --------------------------------------------------------

--
-- Table structure for table `cache`
--

CREATE TABLE `cache` (
  `key` varchar(255) NOT NULL,
  `value` mediumtext NOT NULL,
  `expiration` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `cache_locks`
--

CREATE TABLE `cache_locks` (
  `key` varchar(255) NOT NULL,
  `owner` varchar(255) NOT NULL,
  `expiration` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `failed_jobs`
--

CREATE TABLE `failed_jobs` (
  `id` bigint(20) UNSIGNED NOT NULL,
  `uuid` varchar(255) NOT NULL,
  `connection` text NOT NULL,
  `queue` text NOT NULL,
  `payload` longtext NOT NULL,
  `exception` longtext NOT NULL,
  `failed_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `jobs`
--

CREATE TABLE `jobs` (
  `id` bigint(20) UNSIGNED NOT NULL,
  `queue` varchar(255) NOT NULL,
  `payload` longtext NOT NULL,
  `attempts` tinyint(3) UNSIGNED NOT NULL,
  `reserved_at` int(10) UNSIGNED DEFAULT NULL,
  `available_at` int(10) UNSIGNED NOT NULL,
  `created_at` int(10) UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `job_batches`
--

CREATE TABLE `job_batches` (
  `id` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `total_jobs` int(11) NOT NULL,
  `pending_jobs` int(11) NOT NULL,
  `failed_jobs` int(11) NOT NULL,
  `failed_job_ids` longtext NOT NULL,
  `options` mediumtext DEFAULT NULL,
  `cancelled_at` int(11) DEFAULT NULL,
  `created_at` int(11) NOT NULL,
  `finished_at` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `migrations`
--

CREATE TABLE `migrations` (
  `id` int(10) UNSIGNED NOT NULL,
  `migration` varchar(255) NOT NULL,
  `batch` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `models`
--

CREATE TABLE `models` (
  `id` bigint(20) UNSIGNED NOT NULL,
  `category_id` int(11) NOT NULL,
  `title` varchar(255) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `model_image` varchar(255) NOT NULL,
  `model_file` varchar(255) NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `models`
--

INSERT INTO `models` (`id`, `category_id`, `title`, `description`, `model_image`, `model_file`, `created_at`, `updated_at`) VALUES
(1, 1, 'House Props Fridge', 'House Props : Fridge', 'uploads/thumbs/2025/01/1737395459.png', 'uploads/models/2025/01//678e8d0356398.glb', '2025-01-20 17:50:59', '2025-01-20 17:50:59'),
(2, 1, 'Table Lamp', 'Table Lamp', 'uploads/thumbs/2025/01/1737395604.png', 'uploads/models/2025/01//678e8d94a5987.glb', '2025-01-20 17:53:24', '2025-01-20 17:53:24');

-- --------------------------------------------------------

--
-- Table structure for table `password_reset_tokens`
--

CREATE TABLE `password_reset_tokens` (
  `email` varchar(255) NOT NULL,
  `token` varchar(255) NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `saved_design`
--

CREATE TABLE `saved_design` (
  `id` bigint(20) UNSIGNED NOT NULL,
  `user_id` int(11) NOT NULL,
  `model_id` int(11) NOT NULL,
  `design` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `image` varchar(255) NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `saved_design`
--

INSERT INTO `saved_design` (`id`, `user_id`, `model_id`, `design`, `image`, `created_at`, `updated_at`) VALUES
(1, 1, 1, '[]', 'uploads/saved_design/2025/01//67967cf1f24ba.jpg', '2025-01-26 18:20:34', '2025-01-26 18:20:34'),
(2, 1, 2, '[t:[x:0.576, y:-0.549, z:-1.663], q:[x:0.00, y:0.65, z:0.00, w:0.76]]', 'uploads/saved_design/2025/01//67973ee8eed03.jpg', '2025-01-27 08:08:09', '2025-01-27 08:08:09');

-- --------------------------------------------------------

--
-- Table structure for table `sessions`
--

CREATE TABLE `sessions` (
  `id` varchar(255) NOT NULL,
  `user_id` bigint(20) UNSIGNED DEFAULT NULL,
  `ip_address` varchar(45) DEFAULT NULL,
  `user_agent` text DEFAULT NULL,
  `payload` longtext NOT NULL,
  `last_activity` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `sessions`
--

INSERT INTO `sessions` (`id`, `user_id`, `ip_address`, `user_agent`, `payload`, `last_activity`) VALUES
('3o34NdPOyuPA2CKdx0DSn9XX7ZIhxqp8bjGWxV5R', NULL, '2404:3100:1047:90b1:1:0:beb3:3596', 'okhttp/4.11.0', 'YTozOntzOjY6Il90b2tlbiI7czo0MDoiNU1pTDFHZVFNZFRFT09QYXYyOHZTMzdHdkY2WEVja0JwWWhacWJhNSI7czo5OiJfcHJldmlvdXMiO2E6MTp7czozOiJ1cmwiO3M6NDc6Imh0dHBzOi8vdGdhbGkuY29tL2FyZGVjby9hcmRlY28vYXBpL3YxL21vZGVscy8xIjt9czo2OiJfZmxhc2giO2E6Mjp7czozOiJvbGQiO2E6MDp7fXM6MzoibmV3IjthOjA6e319fQ==', 1738138377),
('5xzNzNjWN9dNfmYKpMwbyKfOJgefYPBXIOWvQZtn', NULL, '182.190.217.231', 'okhttp/4.11.0', 'YTozOntzOjY6Il90b2tlbiI7czo0MDoidVBVNjZJaFpPRXBGTWJwSnd5bkYxODUxYVNLa0w1U1FkUHhaUTREcCI7czo5OiJfcHJldmlvdXMiO2E6MTp7czozOiJ1cmwiO3M6OTA6Imh0dHBzOi8vdGdhbGkuY29tL2FyZGVjby9hcmRlY28vYXBpL3YxL2xvZ2luP2VtYWlsPWFkbWluJTQwYXJkZWNvLmNvbSZwYXNzd29yZD1hZG1pbiU0MDEyMyI7fXM6NjoiX2ZsYXNoIjthOjI6e3M6Mzoib2xkIjthOjA6e31zOjM6Im5ldyI7YTowOnt9fX0=', 1737974071),
('7ZpBjctD8e6BYqrl11AxogyZ2rmoAJ2w1KRl9N32', 2, '182.190.217.231', 'okhttp/4.11.0', 'YTo0OntzOjY6Il90b2tlbiI7czo0MDoiOXNralJhdWRLM0l6THRsVE51QWpMWDdqdFVKMTd6OXhrZGhxZ2JuTCI7czo1MDoibG9naW5fd2ViXzU5YmEzNmFkZGMyYjJmOTQwMTU4MGYwMTRjN2Y1OGVhNGUzMDk4OWQiO2k6MjtzOjk6Il9wcmV2aW91cyI7YToxOntzOjM6InVybCI7czoxMDA6Imh0dHBzOi8vdGdhbGkuY29tL2FyZGVjby9hcmRlY28vYXBpL3YxL2xvZ2luP2VtYWlsPW1hbGloYS5zaG9ybyU0MGdtYWlsLmNvbSZwYXNzd29yZD1tYWxpaGFzaG9ybzIwMjUiO31zOjY6Il9mbGFzaCI7YToyOntzOjM6Im9sZCI7YTowOnt9czozOiJuZXciO2E6MDp7fX19', 1737983390),
('8Bk3MMC05lUYRNiDOkkIahIJF7sz3x4ffK43EgGp', NULL, '182.190.217.231', 'okhttp/4.11.0', 'YTozOntzOjY6Il90b2tlbiI7czo0MDoiWWtpTGhBTk1POUg4WDR2OUxjZ1FoWVNBd1Q3WHFsbmlXcWo0eFZPaSI7czo5OiJfcHJldmlvdXMiO2E6MTp7czozOiJ1cmwiO3M6ODI6Imh0dHBzOi8vdGdhbGkuY29tL2FyZGVjby9hcmRlY28vYXBpL3YxL2xvZ2luP2VtYWlsPWFiYyU0MGdtYWlsLmNvbSZwYXNzd29yZD0xMjN5ODgiO31zOjY6Il9mbGFzaCI7YToyOntzOjM6Im9sZCI7YTowOnt9czozOiJuZXciO2E6MDp7fX19', 1737974021),
('BM0CDD0ZUQNvcUna4TwTZowIk1wJzI1OjsJAaJG3', NULL, '119.155.154.166', 'okhttp/4.11.0', 'YTozOntzOjY6Il90b2tlbiI7czo0MDoiY3l3S2drTmdjWFhJVGJwaDlZVXhmU2NLdXYwenM5UlZsd2hkZGZmOSI7czo5OiJfcHJldmlvdXMiO2E6MTp7czozOiJ1cmwiO3M6NDc6Imh0dHBzOi8vdGdhbGkuY29tL2FyZGVjby9hcmRlY28vYXBpL3YxL21vZGVscy8xIjt9czo2OiJfZmxhc2giO2E6Mjp7czozOiJvbGQiO2E6MDp7fXM6MzoibmV3IjthOjA6e319fQ==', 1737975106),
('D436Q7kmUnBF4iwbopYSNCOZhjJylHGvC3dVUwyi', NULL, '182.190.217.231', 'okhttp/4.11.0', 'YTozOntzOjY6Il90b2tlbiI7czo0MDoiTnc5c3VIWWZmOU5UOTE1eEE3SUhLdzZ6Y2psd21aUkNPa3BCT2VKZyI7czo5OiJfcHJldmlvdXMiO2E6MTp7czozOiJ1cmwiO3M6NDc6Imh0dHBzOi8vdGdhbGkuY29tL2FyZGVjby9hcmRlY28vYXBpL3YxL21vZGVscy8xIjt9czo2OiJfZmxhc2giO2E6Mjp7czozOiJvbGQiO2E6MDp7fXM6MzoibmV3IjthOjA6e319fQ==', 1737991648),
('E5MaXyoMH1k2dml4nkVtUbshJALJxhjAkYNxjvCg', NULL, '182.190.217.231', 'okhttp/4.11.0', 'YTozOntzOjY6Il90b2tlbiI7czo0MDoiZ1VUUGJOWWkxS1FjS0c0QW5scmQ4V0U2NzBGM1lKZkhtVzBDOGgybCI7czo5OiJfcHJldmlvdXMiO2E6MTp7czozOiJ1cmwiO3M6NDc6Imh0dHBzOi8vdGdhbGkuY29tL2FyZGVjby9hcmRlY28vYXBpL3YxL21vZGVscy8yIjt9czo2OiJfZmxhc2giO2E6Mjp7czozOiJvbGQiO2E6MDp7fXM6MzoibmV3IjthOjA6e319fQ==', 1737974309),
('Grbx0mwRxHcwtUFY6D1l6JdiI7p5tdbpaLGUAvbk', NULL, '182.190.217.231', 'okhttp/4.11.0', 'YTozOntzOjY6Il90b2tlbiI7czo0MDoiQlF3OUcwVFpDWDNLTmk5cEViWjlrVTB0WHBnNnFDNTV5cjk3YmdtdCI7czo5OiJfcHJldmlvdXMiO2E6MTp7czozOiJ1cmwiO3M6NDc6Imh0dHBzOi8vdGdhbGkuY29tL2FyZGVjby9hcmRlY28vYXBpL3YxL21vZGVscy8xIjt9czo2OiJfZmxhc2giO2E6Mjp7czozOiJvbGQiO2E6MDp7fXM6MzoibmV3IjthOjA6e319fQ==', 1737975212),
('iyF3R0CCJE0qaEXzsTZ18KADtBErm7pj8dFjmLu1', NULL, '182.190.217.231', 'okhttp/4.11.0', 'YTozOntzOjY6Il90b2tlbiI7czo0MDoidGhVRVFlQ2VqWTRqZ29xQ0Rzc3NlNWpTbEVLSnpidnNhWVNOcE9vZSI7czo5OiJfcHJldmlvdXMiO2E6MTp7czozOiJ1cmwiO3M6NTM6Imh0dHBzOi8vdGdhbGkuY29tL2FyZGVjby9hcmRlY28vYXBpL3YxL3NhdmVkLWRlc2lnbi8yIjt9czo2OiJfZmxhc2giO2E6Mjp7czozOiJvbGQiO2E6MDp7fXM6MzoibmV3IjthOjA6e319fQ==', 1737974420),
('joQlg6Eg6nh7pYruXg3QyKRgBnf2Vo7y0vA0YPTi', 2, '182.190.217.231', 'okhttp/4.11.0', 'YTo0OntzOjY6Il90b2tlbiI7czo0MDoiY1c2UXozWVRjeEFGR1A4Q2RJazY5QlpLWTdwZER3aDBnbU5mMmhrViI7czo1MDoibG9naW5fd2ViXzU5YmEzNmFkZGMyYjJmOTQwMTU4MGYwMTRjN2Y1OGVhNGUzMDk4OWQiO2k6MjtzOjk6Il9wcmV2aW91cyI7YToxOntzOjM6InVybCI7czoxMDA6Imh0dHBzOi8vdGdhbGkuY29tL2FyZGVjby9hcmRlY28vYXBpL3YxL2xvZ2luP2VtYWlsPW1hbGloYS5zaG9ybyU0MGdtYWlsLmNvbSZwYXNzd29yZD1tYWxpaGFzaG9ybzIwMjUiO31zOjY6Il9mbGFzaCI7YToyOntzOjM6Im9sZCI7YTowOnt9czozOiJuZXciO2E6MDp7fX19', 1737974302),
('lueXd3JqXvM2P6Iq92K8G8yQ6raMF2LEXr8J22vy', NULL, '182.190.217.231', 'okhttp/4.11.0', 'YTozOntzOjY6Il90b2tlbiI7czo0MDoid0xyY1Jld3NHdHh2aWFTRUE1NXhsaU5rS05xS3pyQzhrSnpKUURVaCI7czo5OiJfcHJldmlvdXMiO2E6MTp7czozOiJ1cmwiO3M6NDc6Imh0dHBzOi8vdGdhbGkuY29tL2FyZGVjby9hcmRlY28vYXBpL3YxL21vZGVscy8yIjt9czo2OiJfZmxhc2giO2E6Mjp7czozOiJvbGQiO2E6MDp7fXM6MzoibmV3IjthOjA6e319fQ==', 1737991656),
('M7kU8RW8AIeTAF7oKpjozSGX4exUrHRretm9oNsX', NULL, '182.190.217.231', 'okhttp/4.11.0', 'YTozOntzOjY6Il90b2tlbiI7czo0MDoiODZFMUxReFpzREx2ZUFNUlFvaDhWWnhUSU1qeGtsUUVTQ0pFbTYxYiI7czo5OiJfcHJldmlvdXMiO2E6MTp7czozOiJ1cmwiO3M6NDc6Imh0dHBzOi8vdGdhbGkuY29tL2FyZGVjby9hcmRlY28vYXBpL3YxL21vZGVscy8xIjt9czo2OiJfZmxhc2giO2E6Mjp7czozOiJvbGQiO2E6MDp7fXM6MzoibmV3IjthOjA6e319fQ==', 1737983023),
('ojfb4hHIRvPacIy73fwtIVCCLy5TdFPD1pOS12N2', 2, '182.190.217.231', 'okhttp/4.11.0', 'YTo0OntzOjY6Il90b2tlbiI7czo0MDoienhVUElPZWV0RmJxZlBjS1AzM1JKZFFCRkM2RHR3eFB6VXExSnNGbSI7czo1MDoibG9naW5fd2ViXzU5YmEzNmFkZGMyYjJmOTQwMTU4MGYwMTRjN2Y1OGVhNGUzMDk4OWQiO2k6MjtzOjk6Il9wcmV2aW91cyI7YToxOntzOjM6InVybCI7czoxMDA6Imh0dHBzOi8vdGdhbGkuY29tL2FyZGVjby9hcmRlY28vYXBpL3YxL2xvZ2luP2VtYWlsPW1hbGloYS5zaG9ybyU0MGdtYWlsLmNvbSZwYXNzd29yZD1tYWxpaGFzaG9ybzIwMjUiO31zOjY6Il9mbGFzaCI7YToyOntzOjM6Im9sZCI7YTowOnt9czozOiJuZXciO2E6MDp7fX19', 1737983011),
('psCXDvfUd3tuDPYQNMMAx8YKswFdIJql8S3oJs3X', NULL, '182.190.217.231', 'okhttp/4.11.0', 'YTozOntzOjY6Il90b2tlbiI7czo0MDoiV2Y2SEFDVmRnaFVTRHBnQkN4NTE1QUJkcmlmV1czbnhDWVZSWW9ydSI7czo5OiJfcHJldmlvdXMiO2E6MTp7czozOiJ1cmwiO3M6MTUzOiJodHRwczovL3RnYWxpLmNvbS9hcmRlY28vYXJkZWNvL2FwaS92MS9zaWdudXA/ZGF0ZU9mQmlydGg9JmVtYWlsPW1hbGloYS5zaG9ybyU0MGdtYWlsLmNvbSZtb2JpbGU9MDMzMDY3ODUxMjQmbmFtZT1NYWxpaGElMjBTaG9ybyZwYXNzd29yZD1tYWxpaGFzaG9ybzIwMjUiO31zOjY6Il9mbGFzaCI7YToyOntzOjM6Im9sZCI7YTowOnt9czozOiJuZXciO2E6MDp7fX19', 1737974216),
('pZzFPTgBMbeHZx7iRupOCxqcPZvlReOhDTaqRnpR', NULL, '182.190.217.231', 'okhttp/4.11.0', 'YTozOntzOjY6Il90b2tlbiI7czo0MDoiaU9NSXNpdUk3SUtyNkdxckxsc0JIaXlBMXJEWUx5SlZ2MGdYSDd0ZiI7czo5OiJfcHJldmlvdXMiO2E6MTp7czozOiJ1cmwiO3M6MTYxOiJodHRwczovL3RnYWxpLmNvbS9hcmRlY28vYXJkZWNvL2FwaS92MS9zaWdudXA/ZGF0ZU9mQmlydGg9My04LTIwMDImZW1haWw9bWFsaWhhLnNob3JvJTQwZ21haWwuY29tJm1vYmlsZT0wMzMwNjc4NTEyNCZuYW1lPU1hbGloYSUyMFNob3JvJnBhc3N3b3JkPW1hbGloYXNob3JvMjAyNSI7fXM6NjoiX2ZsYXNoIjthOjI6e3M6Mzoib2xkIjthOjA6e31zOjM6Im5ldyI7YTowOnt9fX0=', 1737974278),
('sdMcRnVk5vic83dN75Y722K0daetwTB6UOgVr978', NULL, '2404:3100:100c:d0eb:1:0:156d:c171', 'okhttp/4.11.0', 'YTozOntzOjY6Il90b2tlbiI7czo0MDoiMTY4aE55VzNqTlRVRUYxaXA0TnlUUnRTanE4NlZVcFI2ZzFaaUJ0aCI7czo5OiJfcHJldmlvdXMiO2E6MTp7czozOiJ1cmwiO3M6NDc6Imh0dHBzOi8vdGdhbGkuY29tL2FyZGVjby9hcmRlY28vYXBpL3YxL21vZGVscy8xIjt9czo2OiJfZmxhc2giO2E6Mjp7czozOiJvbGQiO2E6MDp7fXM6MzoibmV3IjthOjA6e319fQ==', 1737975152),
('vaa5COpjxvN5ok5JmvCWZjqBoHTLt4N4a1KUyL6k', 2, '182.190.217.231', 'okhttp/4.11.0', 'YTo0OntzOjY6Il90b2tlbiI7czo0MDoieWoxYnZCVjlXR2ZlTTlVZkR0bUgyUTNJcE93SW1QY2IxSkROWkU0biI7czo1MDoibG9naW5fd2ViXzU5YmEzNmFkZGMyYjJmOTQwMTU4MGYwMTRjN2Y1OGVhNGUzMDk4OWQiO2k6MjtzOjk6Il9wcmV2aW91cyI7YToxOntzOjM6InVybCI7czoxMDA6Imh0dHBzOi8vdGdhbGkuY29tL2FyZGVjby9hcmRlY28vYXBpL3YxL2xvZ2luP2VtYWlsPW1hbGloYS5zaG9ybyU0MGdtYWlsLmNvbSZwYXNzd29yZD1tYWxpaGFzaG9ybzIwMjUiO31zOjY6Il9mbGFzaCI7YToyOntzOjM6Im9sZCI7YTowOnt9czozOiJuZXciO2E6MDp7fX19', 1737975206),
('xuEvprr9FUA7GURB4egPEA6ccuLjFN7DOJQuL7m0', NULL, '182.190.217.231', 'okhttp/4.11.0', 'YTozOntzOjY6Il90b2tlbiI7czo0MDoiZ2w5OEc2VUVuQUg1MGRPMXdGeE1xTVlHMHpxYWhta1ZkdHZxaGU3ZSI7czo5OiJfcHJldmlvdXMiO2E6MTp7czozOiJ1cmwiO3M6NDc6Imh0dHBzOi8vdGdhbGkuY29tL2FyZGVjby9hcmRlY28vYXBpL3YxL21vZGVscy8xIjt9czo2OiJfZmxhc2giO2E6Mjp7czozOiJvbGQiO2E6MDp7fXM6MzoibmV3IjthOjA6e319fQ==', 1737974313);

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` bigint(20) UNSIGNED NOT NULL,
  `name` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `email_verified_at` timestamp NULL DEFAULT NULL,
  `password` varchar(255) NOT NULL,
  `photo` varchar(255) DEFAULT '/user/default.png',
  `remember_token` varchar(100) DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  `dob` varchar(20) NOT NULL,
  `mobile` varchar(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `name`, `email`, `email_verified_at`, `password`, `photo`, `remember_token`, `created_at`, `updated_at`, `dob`, `mobile`) VALUES
(1, 'AR Rahman', 'ar@ar.com', NULL, '$2y$12$4jokLqrr11LDjHXwHT15nu8/n3lzTMHL1xXwI76kXYIpilUkcW/C.', '/user/default.png', NULL, '2025-01-26 17:32:24', '2025-01-26 19:28:17', '26-1-2025', '03666666666'),
(2, 'Maliha Shoro', 'maliha.shoro@gmail.com', NULL, '$2y$12$IflnOV30PkIYtDrtN9VZRegiwKmNTXrZSASuBeP2QRyyoVYjGatQ.', '/user/default.png', NULL, '2025-01-27 10:37:58', '2025-01-27 10:37:58', '3-8-2002', '03306785124');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `cache`
--
ALTER TABLE `cache`
  ADD PRIMARY KEY (`key`);

--
-- Indexes for table `cache_locks`
--
ALTER TABLE `cache_locks`
  ADD PRIMARY KEY (`key`);

--
-- Indexes for table `failed_jobs`
--
ALTER TABLE `failed_jobs`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `failed_jobs_uuid_unique` (`uuid`);

--
-- Indexes for table `jobs`
--
ALTER TABLE `jobs`
  ADD PRIMARY KEY (`id`),
  ADD KEY `jobs_queue_index` (`queue`);

--
-- Indexes for table `job_batches`
--
ALTER TABLE `job_batches`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `migrations`
--
ALTER TABLE `migrations`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `models`
--
ALTER TABLE `models`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `password_reset_tokens`
--
ALTER TABLE `password_reset_tokens`
  ADD PRIMARY KEY (`email`);

--
-- Indexes for table `saved_design`
--
ALTER TABLE `saved_design`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `sessions`
--
ALTER TABLE `sessions`
  ADD PRIMARY KEY (`id`),
  ADD KEY `sessions_user_id_index` (`user_id`),
  ADD KEY `sessions_last_activity_index` (`last_activity`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `users_email_unique` (`email`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `failed_jobs`
--
ALTER TABLE `failed_jobs`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `jobs`
--
ALTER TABLE `jobs`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `migrations`
--
ALTER TABLE `migrations`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `models`
--
ALTER TABLE `models`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `saved_design`
--
ALTER TABLE `saved_design`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
